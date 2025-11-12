package es.deusto.sd.ecoembes.entity;

import java.util.Date;

public class InfoPlanta {
	private PlantaReciclaje planta;
	private double capacidadActual;
	private Date fechaActu;
	public PlantaReciclaje getPlanta() {
		return planta;
	}
	public void setPlanta(PlantaReciclaje planta) {
		this.planta = planta;
	}
	public double getCapacidadActual() {
		return capacidadActual;
	}
	public void setCapacidadActual(double capacidadActual) {
		this.capacidadActual = capacidadActual;
	}
	public Date getFechaActu() {
		return fechaActu;
	}
	public void setFechaActu(Date fechaActu) {
		this.fechaActu = fechaActu;
	}
	public InfoPlanta(PlantaReciclaje planta, double capacidadActual, Date fechaActu) {
		super();
		this.planta = planta;
		this.capacidadActual = capacidadActual;
		this.fechaActu = fechaActu;
	}
	public InfoPlanta() {}
}
