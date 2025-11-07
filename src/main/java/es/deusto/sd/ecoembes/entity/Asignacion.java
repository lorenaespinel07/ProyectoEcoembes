package es.deusto.sd.ecoembes.entity;

import java.util.Date;
import java.util.Objects;

public class Asignacion {
    private long idAsignacion;
    private long idPlanta;
    private long idPersonal;
    private String contacto;
    private Date fechaAsignacion;

    public long getIdAsignacion() {
        return idAsignacion;
    }

    public long getIdPlanta() {
        return idPlanta;
    }

    public long getIdPersonal() {
        return idPersonal;
    }

    public String getContacto() {
        return contacto;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    
    public void setIdAsignacion(long idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public void setIdPlanta(long idPlanta) {
        this.idPlanta = idPlanta;
    }

    public void setIdPersonal(long idPersonal) {
        this.idPersonal = idPersonal;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAsignacion, idPlanta, idPersonal, contacto, fechaAsignacion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asignacion that = (Asignacion) obj;
        return idAsignacion == that.idAsignacion &&
               idPlanta == that.idPlanta &&
               idPersonal == that.idPersonal &&
               Objects.equals(contacto, that.contacto) &&
               Objects.equals(fechaAsignacion, that.fechaAsignacion);
    }

    @Override
    public String toString() {
        return "Asignacion{" +
               "idAsignacion=" + idAsignacion +
               ", idPlanta=" + idPlanta +
               ", idPersonal=" + idPersonal +
               ", contacto='" + contacto + '\'' +
               ", fechaAsignacion=" + fechaAsignacion +
               '}';
    }
    
	void notificarPlanta() {
        //TODO: implementar notificarPlanta
    }
	
	void actualizarCapacidad() {
		//TODO: implementar actualizarCapacidad
	}
}


