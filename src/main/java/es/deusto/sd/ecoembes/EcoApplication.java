package es.deusto.sd.ecoembes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcoApplication {

//    private final EcoController ecoController;
//
//    EcoApplication(EcoController ecoController) {
//        this.ecoController = ecoController;
//    }
    public static void main(String[] args) {
        SpringApplication.run(EcoApplication.class, args);

        System.out.println("=================================================================");
        System.out.println("Swagger UI listo. Accede en:");
        System.out.println("http://localhost:8080/swagger-ui/index.html#"); // O el puerto que uses
        System.out.println("=================================================================");
	}
}
