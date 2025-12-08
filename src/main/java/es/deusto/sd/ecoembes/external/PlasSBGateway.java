package es.deusto.sd.ecoembes.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.deusto.sd.ecoembes.entity.InfoPlanta;
import es.deusto.sd.ecoembes.entity.PlantaReciclaje;

@Component
public class PlasSBGateway implements IPlantaGateway{

	private final String PLAS_SB_URL = "http://localhost:8081/plassb/disponibilidad";
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	private final PlantaReciclaje planta = new PlantaReciclaje("PlasSB Ltd.", 2);
	
	public PlasSBGateway() {
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper();
	}
	/*
	@Override
	public Optional<ArrayList<InfoPlanta>> getInfosPlanta() {
		String fecha1 =  "2025-11-12";
		String fecha2 = LocalDate.now().toString();
		double capacidad1 = getCapacidadPlanta(fecha1);
		double capacidad2 = getCapacidadPlanta(fecha2);
		ArrayList<InfoPlanta> infos = new ArrayList<>();
		LocalDate fecha1date = LocalDate.parse(fecha1);
		LocalDate fecha2date = LocalDate.parse(fecha2);
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
        cal.set(fecha1date.getYear(), fecha1date.getMonthValue() - 1, fecha1date.getDayOfMonth());
        cal2.set(fecha2date.getYear(), fecha2date.getMonthValue() - 1, fecha2date.getDayOfMonth());
		infos.add(new InfoPlanta(planta, capacidad1, cal.getTime()));
		infos.add(new InfoPlanta(planta, capacidad2, cal2.getTime()));
		return Optional.of(infos);
	}
	*/
	public double getCapacidadPlanta(String fecha) {
		String url = PLAS_SB_URL + "?fecha=" + fecha;
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				//System.out.println("Response body: " + response.body());
				//System.out.println(Double.parseDouble(response.body()));
				return Double.parseDouble(response.body());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}
	@Override
	public Optional<InfoPlanta> getInfoPlantaPorFecha(Date fecha) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	@Override
	public Optional<String> enviarAsignacionPlanta(int numeroContenedores, int cantidadEnvases) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
