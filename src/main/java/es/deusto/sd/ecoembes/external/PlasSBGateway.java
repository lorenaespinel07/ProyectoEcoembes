package es.deusto.sd.ecoembes.external;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.deusto.sd.ecoembes.entity.InfoPlanta;
import es.deusto.sd.ecoembes.entity.PlantaReciclaje;
import org.springframework.web.client.RestTemplate;

@Component
public class PlasSBGateway implements IPlantaGateway{

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	private final PlantaReciclaje planta = new PlantaReciclaje("PlasSB Ltd.", 2);
    private String URL_BASE;
    public PlasSBGateway() {
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper();
        this.planta.setIdplanta(1);
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")){
			props.load(fis);
			this.URL_BASE = props.getProperty("PlasSB.server.urlbase");
		} catch (Exception e) {
			e.printStackTrace();
			//this.URL_BASE = "http://localhost:8080/api/plasSB"; // Valor por defecto en caso de error
		}
	}

    @Override
	public double getCapacidadPlanta(String fecha) {
        String url = URL_BASE + "/capacidad?fecha=" + fecha;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 && response.body() != null) {
                double capacidad = Double.parseDouble(response.body());
                return capacidad;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
	}

	@Override
	public Optional<String> enviarAsignacionPlanta(int numeroContenedores, int cantidadEnvases) {
        double toneladas = cantidadEnvases / 1000.0;
        String url = URL_BASE + "/asignacion";

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("fecha", LocalDate.now().toString());
            body.put("toneladas", toneladas);
            body.put("numeroContenedores", numeroContenedores);

            String jsonBody = objectMapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json") // Importante avisar que enviamos JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("✅ Asignación exitosa: " + response.body());
                return Optional.of(response.body());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return Optional.empty();
    }
}