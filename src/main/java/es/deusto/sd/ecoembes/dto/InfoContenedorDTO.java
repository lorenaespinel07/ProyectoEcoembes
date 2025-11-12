package es.deusto.sd.ecoembes.dto;

import es.deusto.sd.ecoembes.entity.NivelLlenado;

public class InfoContenedorDTO {
	Long idContendor;
	int numEstimado;
	NivelLlenado nivelLlenado;
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
	public NivelLlenado getNivelLlenado() {
		return nivelLlenado;
	}
	public void setNivelLlenado(NivelLlenado nivelLlenado) {
		this.nivelLlenado = nivelLlenado;
	}
	
	
}
