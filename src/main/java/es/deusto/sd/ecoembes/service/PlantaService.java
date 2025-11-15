package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service

public class PlantaService {
    static Map<Long, PlantaReciclaje> dbPlantas = new HashMap<>();
    static Map<Long, List<InfoPlanta>> dbInfoPlanta = new HashMap<>();
    static ArrayList<Asignacion> dbAsignacion = new ArrayList<>();

    public void addAsignacion(Asignacion a) {
        dbAsignacion.add(a);
    }
    public void addPlanta(PlantaReciclaje p) {
        dbPlantas.put(p.getIdplanta(), p);
    }
    public void addInfoPlanta(PlantaReciclaje p, ArrayList<InfoPlanta> lista) {dbInfoPlanta.put(p.getIdplanta(), lista);}

    public Optional<?> getInfoPlantasPorFecha(Date fecha){

		ArrayList<InfoPlanta> resultado = new ArrayList<>();
		for (PlantaReciclaje planta : dbPlantas.values()) {
			List<InfoPlanta> listaInfo = dbInfoPlanta.get(planta.getIdplanta());
			if (listaInfo != null) {
				for (InfoPlanta info : listaInfo) {
					if (compraraDias(info.getFechaActu(), fecha)) {
						resultado.add(info);
					}
				}
			} else {
				return Optional.of(new ArrayList<>());
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

