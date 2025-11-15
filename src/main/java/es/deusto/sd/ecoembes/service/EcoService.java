package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
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
            //token no valido
            return Optional.of("UNAUTHORIZED");
        }
        ArrayList<Contenedor> contenedoresAsignados = new ArrayList<>();
        Personal personal = authService.dbTokensActivos.get(token);
        PlantaReciclaje plantaAsignada = null;

        int envaseTotales = 0;
        //Lógica de asignación (simplificada)
        for (Long idContenedor : idsContenedores) {
            Contenedor contenedor = contenedorService.dbContenedor.get(idContenedor);
            if (contenedor != null) {
                //Asignar a la primera planta disponible (simplificado)
                InfoContenedor infoContendor = getUltimaInfoContenedor(idContenedor);
                if (infoContendor != null) {
                    envaseTotales += infoContendor.getNumeroEnvases();
                }
            } else {
                return Optional.of("Contenedor con ID " + idContenedor + " no encontrado.");
            }
        }

        for(PlantaReciclaje planta : PlantaService.dbPlantas.values()) {
            List<InfoPlanta> listaInfo = PlantaService.dbInfoPlanta.get(planta.getIdplanta());

            if (listaInfo != null) {
                System.out.println("Info planta encontrada para: " + planta.getNombre());
                InfoPlanta plantaInfo = listaInfo.get(listaInfo.size() - 1); //Última info
                System.out.println("Capacidad actual planta " + planta.getNombre() + ": " + plantaInfo.getCapacidadActual()*1000);
                if (plantaInfo.getCapacidadActual()* 1000 - envaseTotales >= 0) {
                    //Asignación exitosa
                    plantaAsignada = planta;
                    Asignacion asignacion = new Asignacion(contenedoresAsignados, plantaAsignada, personal, new Date());
                    PlantaService.dbAsignacion.add(asignacion);
                    return Optional.of("Contenedores asignados a la planta " + plantaInfo.getPlanta().getNombre() + " cantidad estimada de envases: "+ envaseTotales);
                }
            }
        }
        return Optional.of("No hay plantas con capacidad suficiente para los envases totales: " + envaseTotales);
    }

    private InfoContenedor getUltimaInfoContenedor(Long idContenedor) {
        List<InfoContenedor> listaInfo = ContenedorService.dbInfoContenedor.get(idContenedor);
        if (listaInfo != null && !listaInfo.isEmpty()) {
            return listaInfo.get(listaInfo.size() - 1);
        }
        return null;
    }


}