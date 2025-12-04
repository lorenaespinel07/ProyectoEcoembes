package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class EcoService {
    @Autowired
    private AuthService authService;
    @Autowired
    private ContenedorService contenedorService;
    @Autowired
    private PlantaService plantaService;

    public Optional<String> login(String c, String p) {
        return authService.login(c, p);
    }
    public Optional<Boolean> logout(String t) {
        return authService.logout(t);
    }
    public Optional<?> crearContenedor(long id, String u, String cp, int cap, String token) {
        if (!authService.validarToken(token)) {
            return Optional.of("Token no válido");
        }
        return contenedorService.crearContenedor(id, u, cp, cap, token);
    }

    public Optional<InfoContenedor> actualizarInfoContenedor(Long id, int numEnvases, String nivelStr){
        return contenedorService.actualizarInfoContenedor(id, numEnvases, nivelStr);
    }

    public Optional<?> getInfoContenedorPorZona(String cp, Date fecha, String token){
        if (!authService.validarToken(token)) {
            return Optional.of("Token no válido");
        }
        return contenedorService.getInfoContenedorPorZona(cp, fecha);
    }

    public Optional<?> getInfoContendorPorFecha(long id, Date f1, Date f2, String token) {
        if (!authService.validarToken(token)) {
            return Optional.of("Token no válido");
        }
        return contenedorService.getInfoContendorPorFecha(id, f1, f2, token);
    }

    public Optional<?> getInfoPlantasPorFecha(Date fecha, String token) {
        if (!authService.validarToken(token)) {
            return Optional.of("Token no válido");
        }
        return plantaService.getInfoPlantasPorFecha(fecha);
    }

    public Optional<String> asignarContenedoresAPlantas(List<Long> idsContenedores, String token){
        if (!authService.validarToken(token)) {
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
        List<PlantaReciclaje> todasLasPlantas = plantaService.getAllPlantas();

        for(PlantaReciclaje planta : todasLasPlantas) {
            List<InfoPlanta> listaInfo = plantaService.getInfoPorPlanta(planta.getIdplanta());

            if (listaInfo != null && !listaInfo.isEmpty()) {
                // Suponemos que la última info es la actual
                InfoPlanta plantaInfo = listaInfo.get(listaInfo.size() - 1);

                // Comprobar capacidad
                if (plantaInfo.getCapacidadActual() * 1000 - envaseTotales >= 0) {

                    // ¡Éxito! Crear asignación y guardar
                    Asignacion asignacion = new Asignacion(contenedoresAsignados, planta, personal, new Date());
                    plantaService.addAsignacion(asignacion);

                    return Optional.of("Contenedores asignados a la planta " + planta.getNombre() +
                            " (Cantidad estimada: "+ envaseTotales + ")");
                }
            }
        }
        return Optional.of("No hay plantas con capacidad suficiente para los envases totales: " + envaseTotales);
    }


//    private InfoContenedor getUltimaInfoContenedor(Long idContenedor) {
//        List<InfoContenedor> listaInfo = ContenedorService.dbInfoContenedor.get(idContenedor);
//        if (listaInfo != null && !listaInfo.isEmpty()) {
//            return listaInfo.get(listaInfo.size() - 1);
//        }
//        return null;
//    }


}