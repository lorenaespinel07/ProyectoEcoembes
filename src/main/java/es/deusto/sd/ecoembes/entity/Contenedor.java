package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contenedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; // identificador 
	
	@Column(nullable = false)
	private String ubicacion; 
	@Column(nullable = false)
	private String cp;
	@Column(nullable = false)
	private int capacidadIni;
	@Enumerated(EnumType.STRING)
	private NivelLlenado nivel;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public int getCapacidadIni() {
		return capacidadIni;
	}
	public void setCapacidadIni(int capacidadIni) {
		this.capacidadIni = capacidadIni;
	}
	public NivelLlenado getNivel() {
		return nivel;
	}
	public void setNivel(NivelLlenado nivel) {
		this.nivel = nivel;
	}
	public Contenedor(long id, String ubicacion, String cp, int capacidadIni, NivelLlenado nivel) {
		super();
		this.id = id;
		this.ubicacion = ubicacion;
		this.cp = cp;
		this.capacidadIni = capacidadIni;
		this.nivel = nivel;
	}
	public Contenedor(long id, String ubicacion, String cp, int capacidadIni) {
		super();
		this.id = id;
		this.ubicacion = ubicacion;
		this.cp = cp;
		this.capacidadIni = capacidadIni;
		this.nivel = NivelLlenado.VERDE;
	}
	public Contenedor() {}
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