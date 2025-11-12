/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.ecoembes;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.ecoembes.entity.Personal;
import es.deusto.sd.ecoembes.service.EcoService;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initData(EcoService ecoservice) {
		return args -> {
			Personal p1 = new Personal("admin@ecoembes.com", "hash_de_1234", "Admin User");
            appService.addPersonal(p1);
            PersonalEcoembes p2 = new PersonalEcoembes("user@ecoembes.com", "hash_de_5678", "Regular User");
            appService.addPersonal(p2);

            // Crear contenedores de prueba
            Contenedor c1 = new Contenedor("C-001", new Ubicacion("Calle Falsa 123", "48001"), 1000.0);
            Contenedor c2 = new Contenedor("C-002", new Ubicacion("Avenida Principal 45", "48001"), 1000.0);
            Contenedor c3 = new Contenedor("C-003", new Ubicacion("Plaza Nueva 1", "48002"), 800.0);
            appService.addContenedor(c1);
            appService.addContenedor(c2);
            appService.addContenedor(c3);
            // Crear historiales de actualizaciones (con datos de ayer y hoy)
            ArrayList<ActualizacionContenedor> actualizacionesc1 = new ArrayList<>(List.of(
                    new ActualizacionContenedor(Timestamp.valueOf(LocalDate.now().minusDays(1).atStartOfDay()), 100, EstadoLlenado.VERDE, c1.getIdentificador())
            ));
            appService.addActualizaciones(c1, actualizacionesc1);
            ArrayList<ActualizacionContenedor> actualizacionesc2 = new ArrayList<>(List.of(
                    new ActualizacionContenedor(Timestamp.valueOf(LocalDate.now().minusDays(1).atStartOfDay()), 500, EstadoLlenado.NARANJA, c2.getIdentificador())
            ));
            appService.addActualizaciones(c2, actualizacionesc2);
            appService.addActualizaciones(c3, new ArrayList<ActualizacionContenedor>());

            // Crear plantas de prueba [cite: 36]
            PlantaReciclaje pl1 = new PlantaReciclaje("PLASSB", "PlasSB Ltd.", "http://plassb.com/api/notificar");
            PlantaReciclaje pl2 = new PlantaReciclaje("CONTSOCKET", "ContSocket Ltd.", "http://contsocket.com/api/notificar");
            appService.addPlanta(pl1);
            appService.addPlanta(pl2);
            // Crear capacidades de prueba [cite: 37]
            ArrayList<CapacidadDiaria> capacidadesDiarias1 = new ArrayList<>(List.of(
                    new CapacidadDiaria(pl1.getIdPlanta(), LocalDate.now(), 100.0),
                    new CapacidadDiaria(pl1.getIdPlanta(), LocalDate.now().plusDays(1), 150.0)
            ));
            appService.addCapacidades(pl1, capacidadesDiarias1);
            ArrayList<CapacidadDiaria> capacidadesDiaries2 = new ArrayList<>(List.of(
                    new CapacidadDiaria(pl2.getIdPlanta(), LocalDate.now(), 80.0)
            ));
            appService.addCapacidades(pl2, capacidadesDiaries2);
		};
	}
}