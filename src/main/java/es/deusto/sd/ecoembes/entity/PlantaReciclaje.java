package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PlantaReciclaje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idplanta;
	@Column(nullable = false, unique = true)
	private String nombre;
	@Column(nullable = false)
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
	@Override
	public int hashCode() {
		return Objects.hash(capacidad, idplanta, nombre);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlantaReciclaje other = (PlantaReciclaje) obj;
		return Double.doubleToLongBits(capacidad) == Double.doubleToLongBits(other.capacidad)
				&& idplanta == other.idplanta && Objects.equals(nombre, other.nombre);
	}
}
