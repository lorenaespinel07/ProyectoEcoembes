package es.deusto.sd.ecoembes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Asignacion {
    //private long idAsignacion
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAsignacion;
    @ManyToOne
    @JoinColumn(name = "personal_id")
    @JsonIgnore
    private Personal personal;

    @ManyToOne
    @JoinColumn(name = "planta_id")
    @JsonIgnore
    private PlantaReciclaje planta;

    @ManyToMany
    @JoinTable(
            name = "asignacion_contenedores",
            joinColumns = @JoinColumn(name = "asignacion_id"),
            inverseJoinColumns = @JoinColumn(name = "contenedor_id"))
    @JsonIgnore
    private List<Contenedor> contenedores = new ArrayList<>();


    public Asignacion(ArrayList<Contenedor> contenedores, PlantaReciclaje planta, Personal personal,
			Date fechaAsignacion) {
		super();
		this.contenedores = contenedores;
		this.planta = planta;
		this.personal = personal;
		this.fechaAsignacion = fechaAsignacion;
	}
	private Date fechaAsignacion;

    public Asignacion() {

    }

    public void setContenedores(ArrayList<Contenedor> contenedores) {
		this.contenedores = contenedores;
	}
	public PlantaReciclaje getPlanta() {
		return planta;
	}
	public void setPlanta(PlantaReciclaje planta) {
		this.planta = planta;
	}
	public Personal getPersonal() {
		return personal;
	}
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}
	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
    
}


