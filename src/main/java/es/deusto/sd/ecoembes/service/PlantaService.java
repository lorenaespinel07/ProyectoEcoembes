package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.dao.AsignacionRepository;
import es.deusto.sd.ecoembes.dao.PlantaReciclajeRepository;
import es.deusto.sd.ecoembes.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
// eder del futuro boy
public class PlantaService {
    @Autowired
    private PlantaReciclajeRepository plantaRepository;	
    @Autowired
    private AsignacionRepository asignacionRepository;
    @Autowired
    private AuthService authService;

    @Autowired
    private ContenedorService contenedorService;

    public void addPlanta(PlantaReciclaje p) {
        plantaRepository.save(p);
    }
    public void addInfoPlanta(PlantaReciclaje p, ArrayList<InfoPlanta> lista) {
        PlantaReciclaje plantaViva = plantaRepository.findById(p.getIdplanta()).orElse(p);

        for (InfoPlanta info : lista) {
            info.setPlanta(plantaViva);
            plantaViva.getHistorial().add(info);
        }
        // Guardar planta (cascada guarda infos)
        plantaRepository.save(plantaViva);
    }
    //GET
    public Optional<?> getInfoPlantasPorFecha(Date fecha, String token) {

        List<PlantaReciclaje> plantas = plantaRepository.findAll();

        ArrayList<InfoPlanta> resultado = new ArrayList<>();

        for (PlantaReciclaje p : plantas) {
            p.getHistorial().stream()
                    .filter(info -> compraraDias(info.getFechaActu(), fecha))
                    .forEach(resultado::add);
        }
        return Optional.of(resultado);
	}

    //POST
    public Optional<String> asignarContenedoresAPlantas(List<Long> idsContenedores, String token){
        if (!AuthService.validarToken(token)) {
            return Optional.of("UNAUTHORIZED");
        }

        // 1. Recuperar Personal (Usuario)
        Personal personal = authService.getPersonalByToken(token);
        if (personal == null) return Optional.of("Usuario no encontrado para ese token");

        ArrayList<Contenedor> contenedoresAsignados = new ArrayList<>();
        int envaseTotales = 0;

        // 2. Recuperar Contenedores (usando Servicio, no mapa estático)
        for (Long idContenedor : idsContenedores) {
            Optional<Contenedor> contenedorOpt = contenedorService.buscarPorId(idContenedor);

            if (contenedorOpt.isPresent()) {
                contenedoresAsignados.add(contenedorOpt.get());

                // Obtener última info (lógica delegada al servicio)
                InfoContenedor info = contenedorService.getUltimaInfoContenedor(idContenedor);
                if (info != null) {
                    envaseTotales += info.getNumeroEnvases();
                }
            } else {
                return Optional.of("Contenedor con ID " + idContenedor + " no encontrado.");
            }
        }

        // 3. Buscar Planta (usando Servicio, no mapa estático)
        List<PlantaReciclaje> todasLasPlantas = plantaRepository.findAll();

        for(PlantaReciclaje planta : todasLasPlantas) {
            List<InfoPlanta> listaInfo = getInfoPorPlanta(planta.getIdplanta());
            if (listaInfo != null && !listaInfo.isEmpty()) {
                // Suponemos que la última info es la actual
                InfoPlanta plantaInfo = listaInfo.get(listaInfo.size() - 1);

                // Comprobar capacidad
                if (plantaInfo.getCapacidadActual() * 1000 - envaseTotales >= 0) {
                    Asignacion asignacion = new Asignacion(contenedoresAsignados, planta, personal, new Date());
                    asignacionRepository.save(asignacion);
                    return Optional.of("Contenedores asignados a la planta " + planta.getNombre() +
                            " (Cantidad estimada: "+ envaseTotales + ")");
                }
            }
        }
        return Optional.of("No hay plantas con capacidad suficiente para los envases totales: " + envaseTotales);
    }

    private static synchronized boolean compraraDias(Date fecha1, Date fecha2) {
		LocalDate localDate1 = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = fecha2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.equals(localDate2);
	}
    public List<InfoPlanta> getInfoPorPlanta(Long idPlanta) {
        Optional<PlantaReciclaje> planta = plantaRepository.findById(idPlanta);
        if (planta.isPresent()) {
            return planta.get().getHistorial();
        }
        return new ArrayList<>();
    }
//    private PlantaFactory plantaFactory; // Asegúrate de tener la factoría inyectada
//
//    public void notificarEnvioExterno(String nombrePlanta, Date fecha, double envasesTotales) {
//        // 1. Convertir fecha
//        String fechaStr = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
//
//        // 2. Convertir envases a toneladas (aprox 1000 envases = 1 tonelada, ajusta tu lógica)
//        double toneladas = envasesTotales / 1000.0;
//
//        // 3. Buscar el gateway correcto
//        PlantaFactory.tipoPlanta tipo = null;
//        if (nombrePlanta.equalsIgnoreCase("PlasSB Ltd.")) tipo = PlantaFactory.tipoPlanta.PlasSB;
//        else if (nombrePlanta.equalsIgnoreCase("ContSocket Ltd.")) tipo = PlantaFactory.tipoPlanta.ContSocket;
//
//        if (tipo != null) {
//            IPlantaGateway gateway = plantaFactory.getPlanta(tipo);
//            if (gateway != null) {
//                gateway.enviarResiduos(fechaStr, toneladas);
//                System.out.println("Notificación enviada a " + nombrePlanta);
//            }
//        }
//    }
}

