package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

public class PlantaReciclaje {
	private long idplanta;
	private String nombre;
	private String ubicacion;
	private int contacto;
	private double capacidadDiariaToneladas;
	
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
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getContacto() {
		return contacto;
	}
	public void setContacto(int contacto) {
		this.contacto = contacto;
	}
	public double getCapacidadDiariaToneladas() {
		return capacidadDiariaToneladas;
	}
	public void setCapacidadDiariaToneladas(double capacidadDiariaToneladas) {
		this.capacidadDiariaToneladas = capacidadDiariaToneladas;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(capacidadDiariaToneladas, contacto, idplanta, nombre, ubicacion);
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
		return Double.doubleToLongBits(capacidadDiariaToneladas) == Double
				.doubleToLongBits(other.capacidadDiariaToneladas) && contacto == other.contacto
				&& idplanta == other.idplanta && Objects.equals(nombre, other.nombre)
				&& Objects.equals(ubicacion, other.ubicacion);
	}

	@Override
	public String toString() {
		return "PlantaReciclaje [idplanta=" + idplanta + ", nombre=" + nombre + ", ubicacion=" + ubicacion
				+ ", contacto=" + contacto + ", capacidadDiariaToneladas=" + capacidadDiariaToneladas + "]";
	}
	
	void notificarRecepcion() {
		// TODO Auto-generated method stub
	}
	
	void actualizarCapacidad() {
		// TODO Auto-generated method stub
	}
}
