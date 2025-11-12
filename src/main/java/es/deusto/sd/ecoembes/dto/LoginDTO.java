package es.deusto.sd.ecoembes.dto;

public class LoginDTO {
	String correo;
	String contraseña;
	
	public String getCorreo() {
		return correo;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
