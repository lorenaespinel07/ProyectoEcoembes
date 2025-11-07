package es.deusto.sd.ecoembes.entity;

import java.util.Date;
import java.util.Objects;

public class Contenedor {
	private long id;
	private long idPlanta;
	private String ubicacion;
	private Double llenado;
	private Date ultimaActualizacion;
	private String tipoContenedor;
	
	public Contenedor(long id,long idPlanta, String ubicacion, Double llenado, Date ultimaActualizacion,
			String tipoContenedor) {
		this.id = id;
		this.idPlanta = idPlanta;
		this.ubicacion = ubicacion;
		this.llenado = llenado;
		this.ultimaActualizacion = ultimaActualizacion;
		this.tipoContenedor = tipoContenedor;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdPlanta() {
		return idPlanta;
	}

	public void setIdPlanta(long idPlanta) {
		this.idPlanta = idPlanta;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Double getLlenado() {
		return llenado;
	}

	public void setLlenado(Double llenado) {
		this.llenado = llenado;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getTipoContenedor() {
		return tipoContenedor;
	}

	public void setTipoContenedor(String tipoContenedor) {
		this.tipoContenedor = tipoContenedor;
	}
	
	//Metodos adicionales
	public Double obtenerNivelLLenado() {
		return this.llenado;
	}
	public void actualizarInformacion() {
		//TODO: implementar 
	}
	// hashCode and equals
		@Override
		public int hashCode() {
			return Objects.hash();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Contenedor other = (Contenedor) obj;
			return id == other.id;
		}
	
}