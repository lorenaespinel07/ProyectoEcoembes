package es.deusto.sd.ecoembes.dto;

public class InfoContenedorDTO {
	Long idContendor;
	int numEstimado;
	String nivelLlenado;
	String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getIdContendor() {
		return idContendor;
	}
	public void setIdContendor(Long idContendor) {
		this.idContendor = idContendor;
	}
	public int getNumEstimado() {
		return numEstimado;
	}
	public void setNumEstimado(int numEstimado) {
		this.numEstimado = numEstimado;
	}
	public String getNivelLlenado() {
		return nivelLlenado;
	}
	public void setNivelLlenado(String nivelLlenado) {
		this.nivelLlenado = nivelLlenado;
	}
	
	
}
