package es.deusto.sd.ecoembes.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal")
public class Personal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idpersonal;
	
	@Column(nullable = false, unique= true)
    private String nombre;
	@Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password; //Esto no es muy seguro btw
	
    
    public Personal(long idpersonal, String nombre, String email, String password) {
		super();
		this.idpersonal = idpersonal;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getIdpersonal() {
		return idpersonal;
	}
	public void setIdpersonal(long idpersonal) {
		this.idpersonal = idpersonal;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, idpersonal, nombre, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personal other = (Personal) obj;
		return Objects.equals(email, other.email) && idpersonal == other.idpersonal
				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password);
	}
    
}	

