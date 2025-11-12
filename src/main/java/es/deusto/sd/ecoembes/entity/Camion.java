package es.deusto.sd.ecoembes.entity;

public class Camion {
	
	private String matricula;
	private Double capacidadCarga;

    public Camion(String matricula, Double capacidadCarga) {
        this.matricula = matricula;
        this.capacidadCarga = capacidadCarga;
    }

    public Camion() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(Double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

}