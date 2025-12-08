package es.deusto.sd.ecoembes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.deusto.sd.ecoembes.dao.ContenedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.ecoembes.entity.*;
import es.deusto.sd.ecoembes.external.IPlantaGateway;
import es.deusto.sd.ecoembes.external.PlantaFactory;
import es.deusto.sd.ecoembes.external.PlantaFactory.tipoPlanta;
import es.deusto.sd.ecoembes.service.AuthService;
import es.deusto.sd.ecoembes.service.ContenedorService;
import es.deusto.sd.ecoembes.service.PlantaService;

@Configuration
public class DataInitializer {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    @Bean
    CommandLineRunner initData(AuthService authService,
                               ContenedorService contenedorService,
                               PlantaService plantaService,
                               ContenedorRepository contenedorRepository) {

        return args -> {
            
        	if (contenedorRepository.count() > 0){        		
        		for (PlantaReciclaje p : plantaService.getAllPlantas()) {
        			System.out.println("Planta existente: " + p.getNombre());
        		}
        		for (String token : AuthService.dbTokensActivos.keySet()) {
        			System.out.println("Token activo existente: " + token);
        		}
        		for (Contenedor c : contenedorService.getAllContenedores()) {
        			System.out.println("Contenedor existente: " + c.getUbicacion());
        		}
                Personal p1 = new Personal("Paquito el pro", "admin@ecoembes.com", "ojolero");
        		authService.addTokenActivo("1", p1);
                return;
            }

            Personal p1 = new Personal("Paquito el pro", "admin@ecoembes.com", "ojolero");
            authService.addPersonal(p1);

            Personal p2 = new Personal("Regular User", "user@ecoembes.com", "sincontraseña");
            authService.addPersonal(p2);
            
            authService.addTokenActivo("1", p1);
            authService.addTokenActivo("9876yhn54gh", p2);


            Contenedor c1 = new Contenedor("Calle de los muertos", "4001", 1000);
            Contenedor c2 = new Contenedor("Avenida Principal 45", "4001", 2000);
            Contenedor c3 = new Contenedor("Plaza Nueva 1", "4802", 800);

            contenedorService.addContenedor(c1);
            contenedorService.addContenedor(c2);
            contenedorService.addContenedor(c3);

            // Fechas
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.NOVEMBER, 12, 3, 00);
            Calendar cal2 = Calendar.getInstance();
            cal2.set(2025, Calendar.SEPTEMBER, 13, 3, 00);
            Calendar cal3 = Calendar.getInstance();
            cal3.set(2025, Calendar.APRIL, 14, 3, 00);
            Calendar cal4 = Calendar.getInstance();
            cal4.set(2025, Calendar.JUNE, 15, 3, 00);

            ArrayList<InfoContenedor> actualizacionesc1 = new ArrayList<>(List.of(
                    new InfoContenedor(c1, 400, NivelLlenado.VERDE, cal.getTime()),
                    new InfoContenedor(c1, 800, NivelLlenado.NARANJA, cal2.getTime()),
                    new InfoContenedor(c1, 50000, NivelLlenado.ROJO, cal3.getTime())
            ));
            contenedorService.addInfoContenedor(c1, actualizacionesc1);

            ArrayList<InfoContenedor> actualizacionesc2 = new ArrayList<>(List.of(
                    new InfoContenedor(c2, 500, NivelLlenado.VERDE, cal.getTime()),
                    new InfoContenedor(c2, 1500, NivelLlenado.NARANJA, cal2.getTime())
            ));
            contenedorService.addInfoContenedor(c2, actualizacionesc2);

            ArrayList<InfoContenedor> actualizacionesc3 = new ArrayList<>(List.of(
                    new InfoContenedor(c3, 200, NivelLlenado.VERDE, cal2.getTime()),
                    new InfoContenedor(c3, 600, NivelLlenado.NARANJA, cal3.getTime()),
                    new InfoContenedor(c3, 800, NivelLlenado.ROJO, cal4.getTime())
            ));
            contenedorService.addInfoContenedor(c3, actualizacionesc3);


            PlantaFactory factory = new PlantaFactory();
            PlantaReciclaje pl1 = new PlantaReciclaje("PlasSB", 2);
            PlantaReciclaje pl2 = new PlantaReciclaje("ContSocket", 4);
            //PlantaReciclaje pl3 = new PlantaReciclaje("EcoRecicla", 3);

            plantaService.addPlanta(pl1);
            plantaService.addPlanta(pl2);
            //plantaService.addPlanta(pl3);

            /*
            ArrayList<InfoPlanta> infoPlanta1 = new ArrayList<>(List.of(
                    new InfoPlanta(pl1, 1.5, cal.getTime()),
                    new InfoPlanta(pl1, 1.0, cal2.getTime())
            ));
            plantaService.addInfoPlanta(pl1, infoPlanta1);
            IPlantaGateway plasSBPlanta = factory.getPlanta(tipoPlanta.PlasSB);
            if (plasSBPlanta != null) {
                var infosOpt = plasSBPlanta.getInfosPlanta();
                if (infosOpt.isPresent()) {
                    List<InfoPlanta> infos = infosOpt.get();
                    infos.forEach(i -> i.setPlanta(pl1));
                    plantaService.addInfoPlanta(pl1, new ArrayList<>(infos));
                }
            }
            */
            
            /*
            IPlantaGateway contsocketPlanta = factory.getPlanta(tipoPlanta.ContSocket);
            if (contsocketPlanta != null) {
                var infosOpt = contsocketPlanta.getInfosPlanta();
                if (infosOpt.isPresent()) {
                    List<InfoPlanta> infos = infosOpt.get();
                    // IMPORTANTE: Asignamos la planta padre a cada info antes de guardar
                    infos.forEach(i -> i.setPlanta(pl2));
                    plantaService.addInfoPlanta(pl2, new ArrayList<>(infos));
                }
            }
            ArrayList<InfoPlanta> infoPlanta2 = new ArrayList<>(List.of(
                    new InfoPlanta(pl2, 3.0, cal.getTime()),
                    new InfoPlanta(pl2, 2.5, cal3.getTime())
            ));
            plantaService.addInfoPlanta(pl2, infoPlanta2);
			*/
            /*
            ArrayList<InfoPlanta> infoPlanta3 = new ArrayList<>(List.of(
            		new InfoPlanta(pl3, 2.0, cal3.getTime()),
            		new InfoPlanta(pl3, 1.0, cal4.getTime())
            		));
            plantaService.addInfoPlanta(pl3, infoPlanta3);
            */

        };
    }
}