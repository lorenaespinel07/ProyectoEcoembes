package es.deusto.sd.ecoembes.external;

import java.util.ArrayList;
import java.util.Optional;

import es.deusto.sd.ecoembes.entity.InfoPlanta;

public interface IPlantaGateway {
	public Optional<ArrayList<InfoPlanta>> getInfosPlanta();
}
