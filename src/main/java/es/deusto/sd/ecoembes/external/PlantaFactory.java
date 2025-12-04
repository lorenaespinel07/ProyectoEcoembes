package es.deusto.sd.ecoembes.external;

public class PlantaFactory {
	public enum tipoPlanta{
		ContSocket,
		PlasSB
	}
	
	public IPlantaGateway getPlanta(tipoPlanta tipo) {
		switch(tipo) {
			case ContSocket:
				return new ContSocketGateway();
			case PlasSB:
				return new PlasSBGateway();
			default:
				return null;
		}
	}

}
