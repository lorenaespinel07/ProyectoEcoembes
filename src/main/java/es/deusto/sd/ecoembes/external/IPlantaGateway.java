package es.deusto.sd.ecoembes.external;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import es.deusto.sd.ecoembes.entity.InfoPlanta;

public interface IPlantaGateway {
	public double getCapacidadPlanta(String fecha);
	public Optional<String> enviarAsignacionPlanta(int numeroContenedores, int cantidadEnvases);
}
