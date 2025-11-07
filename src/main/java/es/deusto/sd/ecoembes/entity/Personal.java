package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

public class Personal {
    private long idpersonal;
    private String nombre;
    private String email;

    
    public long getIdpersonal() {
        return idpersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    
    public void setIdpersonal(long idpersonal) {
        this.idpersonal = idpersonal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpersonal, nombre, email);
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Personal personal = (Personal) obj;
        return idpersonal == personal.idpersonal &&
               Objects.equals(nombre, personal.nombre) &&
               Objects.equals(email, personal.email);
    }

    @Override
    public String toString() {
        return "Personal{" +
               "idpersonal=" + idpersonal +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               '}';
    }

	void descargarInfoContenedor() {
		//TODO Auto-generated method stub
	}

	void consultarCacidadPlanta() {
        //TODO Auto-generated method stub
	}
	
	void asignarContenedores() {
		// TODO Auto-generated method stub
	}
	
}	

