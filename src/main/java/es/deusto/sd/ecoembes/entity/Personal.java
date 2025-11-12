package es.deusto.sd.ecoembes.entity;

public class Personal {
    private long idpersonal;
    private String nombre;
    private String email;
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
    
}	

