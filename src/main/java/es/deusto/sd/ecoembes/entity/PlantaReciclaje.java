package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

public class PlantaReciclaje {
	private long idplanta;
	private String nombre;
	private double capacidad; //Toneladas 
	
	public PlantaReciclaje(long idplanta, String nombre, double capacidad) {
		super();
		this.idplanta = idplanta;
		this.nombre = nombre;
		this.capacidad = capacidad;
	}
	public PlantaReciclaje() {}
	
	public double getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}
	public long getIdplanta() {
		return idplanta;
	}
	public void setIdplanta(long idplanta) {
		this.idplanta = idplanta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
