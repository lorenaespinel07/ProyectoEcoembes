/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.ecoembes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import es.deusto.sd.ecoembes.entity.*;
import es.deusto.sd.ecoembes.service.EcoService;

@Configuration
public class DataInitializer {

    @SuppressWarnings("unused")
	private final EcoApplication ecoApplication;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    DataInitializer(EcoApplication ecoApplication) {
        this.ecoApplication = ecoApplication;
    }
	
    @Bean
    CommandLineRunner initData(EcoService ecoservice) {
		return args -> {
			Personal p1 = new Personal(12345 ,"Paquito el pro","admin@ecoembes.com", "ojolero" );
            ecoservice.addPersonal(p1);
            Personal p2 = new Personal(9876,"user@ecoembes.com", "sincontraseña", "Regular User");
            ecoservice.addPersonal(p2);
            
            Token token1 = new Token("1", p1);
            ecoservice.addTokenActivo(token1.getToken(), p1);
            Token token2 = new Token("9876yhn54gh", p2);
            ecoservice.addTokenActivo(token2.getToken(), p2);
            
            // Crear contenedores de prueba
            Contenedor c1 = new Contenedor(11, "Calle de los muertos", "4001", 1000);
            Contenedor c2 = new Contenedor(22, "Avenida Principal 45", "4001", 2000);
            Contenedor c3 = new Contenedor(33, "Plaza Nueva 1", "4802", 800);
            ecoservice.addContenedor(c1);
            ecoservice.addContenedor(c2);
            ecoservice.addContenedor(c3);
            
            //Fecha con calendar
            Calendar cal = Calendar.getInstance();
            cal.set(2025, Calendar.NOVEMBER, 12, 3, 00);
            
            Calendar cal2 = Calendar.getInstance();
            cal2.set(2025, Calendar.SEPTEMBER, 13, 3, 00);
            
            Calendar cal3 = Calendar.getInstance();
            cal3.set(2025, Calendar.APRIL, 14, 3, 00);
            
            Calendar cal4 = Calendar.getInstance();
            cal4.set(2025, Calendar.JUNE, 15, 3, 00);
            
            // Crear historiales de actualizaciones 
			ArrayList<InfoContenedor> actualizacionesc1 = new ArrayList<>(List.of(
					new InfoContenedor(c1.getId(), 400, NivelLlenado.VERDE, cal.getTime()),
					new InfoContenedor(c1.getId(), 800, NivelLlenado.NARANJA, cal2.getTime()),
					new InfoContenedor(c1.getId(), 5000, NivelLlenado.ROJO, cal3.getTime())
					));
			
			
            ecoservice.addInfoContenedor(c1, actualizacionesc1);
            
            ArrayList<InfoContenedor> actualizacionesc2 = new ArrayList<>(List.of(
            		new InfoContenedor(c2.getId(), 500, NivelLlenado.VERDE, cal.getTime()),
            		new InfoContenedor(c2.getId(), 1500, NivelLlenado.NARANJA, cal2.getTime())
            		));
            ecoservice.addInfoContenedor(c2, actualizacionesc2);
            
			ArrayList<InfoContenedor> actualizacionesc3 = new ArrayList<>(List.of(
					new InfoContenedor(c3.getId(), 200, NivelLlenado.VERDE, cal2.getTime()),
					new InfoContenedor(c3.getId(), 600, NivelLlenado.NARANJA, cal3.getTime()),
					new InfoContenedor(c3.getId(), 800, NivelLlenado.ROJO, cal4.getTime())
					));
			ecoservice.addInfoContenedor(c3, actualizacionesc3);

            

            // Crear plantas de prueba  
            PlantaReciclaje pl1 = new PlantaReciclaje(1245, "PlasSB Ltd.", 2);
            PlantaReciclaje pl2 = new PlantaReciclaje(489, "ContSocket Ltd.", 4);
            PlantaReciclaje pl3 = new PlantaReciclaje(777, "EcoRecicla SA.", 3);
            ecoservice.addPlanta(pl1);
            ecoservice.addPlanta(pl2);
            ecoservice.addPlanta(pl3);
            // Crear capacidades de prueba 
            ArrayList<InfoPlanta> infoPlanta1 = new ArrayList<>(List.of(
                    new InfoPlanta(pl1, 1.5,cal.getTime()),
                    new InfoPlanta(pl1, 10.0,cal2.getTime())
            ));
            ecoservice.addInfoPlanta(pl1, infoPlanta1);
			ArrayList<InfoPlanta> infoPlanta2 = new ArrayList<>(List.of(
					new InfoPlanta(pl2, 3.0, cal.getTime()),
					new InfoPlanta(pl2, 2.5, cal3.getTime())
					));
			ecoservice.addInfoPlanta(pl2, infoPlanta2);
			

			ArrayList<InfoPlanta> infoPlanta3 = new ArrayList<>(List.of(
					new InfoPlanta(pl3, 2.0, cal3.getTime()),
					new InfoPlanta(pl3, 1.0, cal4.getTime())
					));
			
			ecoservice.addInfoPlanta(pl3, infoPlanta3);
		};
	}
}