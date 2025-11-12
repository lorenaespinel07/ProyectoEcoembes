package es.deusto.sd.ecoembes.dto;

import java.util.List;

public class AsignacionDTO {
	private List<Long> idContenedores;
	private String token;
	
	
	public List<Long> getIdContenedores() {
		return idContenedores;
	}
	public void setIdContenedores(List<Long> idContenedores) {
		this.idContenedores = idContenedores;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
