package es.deusto.sd.ecoembes.entity;

import java.util.ArrayList;
import java.util.Date;

public class Asignacion {
    //private long idAsignacion
    private ArrayList<Contenedor> contenedores;
    private PlantaReciclaje planta;
    private Personal personal;
    public Asignacion(ArrayList<Contenedor> contenedores, PlantaReciclaje planta, Personal personal,
			Date fechaAsignacion) {
		super();
		this.contenedores = contenedores;
		this.planta = planta;
		this.personal = personal;
		this.fechaAsignacion = fechaAsignacion;
	}
	private Date fechaAsignacion;
//	public long getIdAsignacion() {
//		return idAsignacion;
//	}
//	public void setIdAsignacion(long idAsignacion) {
//		this.idAsignacion = idAsignacion;
//	}
	public ArrayList<Contenedor> getContenedores() {
		return contenedores;
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


