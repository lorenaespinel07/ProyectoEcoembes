/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.ecoembes;

import es.deusto.sd.ecoembes.facade.AuctionsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuctionsApplication {

    private final AuctionsController auctionsController;

    AuctionsApplication(AuctionsController auctionsController) {
        this.auctionsController = auctionsController;
    }
    public static void main(String[] args) {
        System.out.println(Estado.getEstado(68));
        SpringApplication.run(AuctionsApplication.class, args); // Qué pasa aquí exactamente?
	}
}
