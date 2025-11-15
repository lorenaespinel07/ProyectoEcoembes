package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.entity.Contenedor;
import es.deusto.sd.ecoembes.entity.InfoContenedor;
import es.deusto.sd.ecoembes.entity.NivelLlenado;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ContenedorService {
    static Map<Long, Contenedor> dbContenedor = new HashMap<>();
    static Map<Long, List<InfoContenedor>> dbInfoContenedor = new HashMap<>();

    public void addContenedor(Contenedor c) {
        dbContenedor.put(c.getId(), c);
    }
    public void addInfoContenedor(Contenedor c, ArrayList<InfoContenedor> lista) {dbInfoContenedor.put(c.getId(), lista);}

    public Optional<InfoContenedor> actualizarInfoContenedor(Long idContenedor, int numEnvases, String nivelLlenadoStr){
        Contenedor contenedor = dbContenedor.get(idContenedor);
        if (contenedor != null) {
            NivelLlenado nivelLlenado = NivelLlenado.valueOf(nivelLlenadoStr);
            InfoContenedor infoContenedor = new InfoContenedor(idContenedor, numEnvases, nivelLlenado,new Date());
            List<InfoContenedor> listaInfo = dbInfoContenedor.get(idContenedor);
            if (listaInfo == null) {
                listaInfo = new ArrayList<>();
                dbInfoContenedor.put(idContenedor, listaInfo);
            }
            listaInfo.add(infoContenedor);
            //Actualizar el nivel del contenedor
            contenedor.setNivel(nivelLlenado);
            return Optional.of(infoContenedor);
        } else {
            // El contenedor a actualizar no existe
            return Optional.empty();
        }
    }
    //POST
    public Optional<?> crearContenedor(long idConetendor, String ubicacion, String cp, int capacidadIni, String token){

        if (!dbContenedor.containsKey(idConetendor)) {
            Contenedor contenedor = new Contenedor(idConetendor, ubicacion, cp, capacidadIni);
            dbContenedor.put(idConetendor, contenedor);
            return Optional.of(contenedor);
        }else {
            //el conetendor ya existe
            return Optional.empty();
        }
    }
    //GET
    public Optional<?> getInfoContendorPorFecha(long idContendor, Date fechaInicio, Date fechaFin, String token){

        Contenedor contenedor = dbContenedor.get(idContendor);
        if (contenedor != null) {
            List<InfoContenedor> listaInfo = dbInfoContenedor.get(idContendor);
            if (listaInfo != null) {
                ArrayList<InfoContenedor> resultado = new ArrayList<>();
                for (InfoContenedor info : listaInfo) {
                    if (info.getFechaActu().after(fechaInicio) && info.getFechaActu().before(fechaFin)) {
                        resultado.add(info);
                    }
                }
                return Optional.of(resultado);
            } else {
                // No hay información para este contenedor
                return Optional.empty();
            }
        } else {
            // El contenedor no existe
            return Optional.empty();
        }
    }


    //GET
    public Optional<?> getInfoContenedorPorZona(String cp, Date fecha){

        //LocalDate fechaLocal = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ArrayList<InfoContenedor> resultado = new ArrayList<>();
        for (Contenedor contenedor : dbContenedor.values()) {
            if (contenedor.getCp().equals(cp)) {
                List<InfoContenedor> listaInfo = dbInfoContenedor.get(contenedor.getId());
                if (listaInfo != null) {
                    for (InfoContenedor info : listaInfo) {
                        //LocalDate fechaDia =  info.getFechaActu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (compraraDias(info.getFechaActu(), fecha)) {
                            resultado.add(info);
                        }
                    }
                } else {
                    return Optional.of(new ArrayList<>());
                }
            }
        }
        return Optional.of(resultado);
    }


    private static synchronized boolean compraraDias(Date fecha1, Date fecha2) {
        LocalDate localDate1 = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = fecha2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.equals(localDate2);
    }


}
