package es.deusto.sd.ecoembes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.deusto.sd.ecoembes.facade.EcoController;

@SpringBootApplication
public class EcoApplication {

//    private final EcoController ecoController;
//
//    EcoApplication(EcoController ecoController) {
//        this.ecoController = ecoController;
//    }
    public static void main(String[] args) {
        SpringApplication.run(EcoApplication.class, args); 
	}
}
