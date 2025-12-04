package es.deusto.sd.ecoembes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
@Entity
public class PlantaReciclaje {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long idplanta;
	@Column(nullable = false, unique = true)
	private String nombre;
	@Column(nullable = false)
	private double capacidad; //Toneladas

    @OneToMany(mappedBy = "planta", cascade = CascadeType.ALL)
    private List<InfoPlanta> historial = new ArrayList<>();

    // Una Planta -> Muchas Asignaciones recibidas
    @OneToMany(mappedBy = "planta")
    private List<Asignacion> asignacionesRecibidas = new ArrayList<>();
	
	public PlantaReciclaje(String nombre, double capacidad) {
		super();

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
