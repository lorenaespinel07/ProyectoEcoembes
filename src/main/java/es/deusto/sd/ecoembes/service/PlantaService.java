package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.dao.AsignacionRepository;
import es.deusto.sd.ecoembes.dao.InfoPlantaRepository;
import es.deusto.sd.ecoembes.dao.PlantaReciclajeRepository;
import es.deusto.sd.ecoembes.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Service
// eder del futuro boy
public class PlantaService {
    @Autowired
    private PlantaReciclajeRepository plantaRepository;
    @Autowired
    private InfoPlantaRepository infoPlantaRepository;
    @Autowired
    private AsignacionRepository asignacionRepository;

    public void addAsignacion(Asignacion a) {
        asignacionRepository.save(a);
    }
    public void addPlanta(PlantaReciclaje p) {
        plantaRepository.save(p);
    }
    public void addInfoPlanta(PlantaReciclaje p, ArrayList<InfoPlanta> lista) {
        infoPlantaRepository.saveAll(lista);
    }
    public Optional<?> getInfoPlantasPorFecha(Date fecha){

        List<PlantaReciclaje> plantas = plantaRepository.findAll();
        List<InfoPlanta> todasInfos = infoPlantaRepository.findAll();

        ArrayList<InfoPlanta> resultado = new ArrayList<>();

        for (PlantaReciclaje p : plantas) {
            todasInfos.stream()
                    .filter(info -> info.getPlanta().getIdplanta() == p.getIdplanta()) // Ajustar según tus getters
                    .filter(info -> compraraDias(info.getFechaActu(), fecha))
                    .forEach(resultado::add);
        }
        return Optional.of(resultado);

	}
    // Métodos helper para EcoService
    public List<PlantaReciclaje> getAllPlantas() {
        return plantaRepository.findAll();
    }

    public List<InfoPlanta> getInfoPorPlanta(Long idPlanta) {
        return infoPlantaRepository.findAll().stream()
                .filter(i -> i.getPlanta().getIdplanta() == idPlanta)
                .collect(Collectors.toList());
    }


    private static synchronized boolean compraraDias(Date fecha1, Date fecha2) {
		LocalDate localDate1 = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = fecha2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.equals(localDate2);
	}


}

