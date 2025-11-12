package es.deusto.sd.ecoembes.dto;

public class ContenedorDTO {
	private int idContenedor;
	private String token;
	private String direccion;
	private String cp;
	int capacidadIni;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getIdContenedor() {
		return idContenedor;
	}
	public void setIdContenedor(int idContenedor) {
		this.idContenedor = idContenedor;
	}

	public int getCapacidadIni() {
		return capacidadIni;
	}
	public void setCapacidadIni(int capacidadIni) {
		this.capacidadIni = capacidadIni;
	}
	public String getDireccion() {
		return this.direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCp() {
		return this.cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
}
