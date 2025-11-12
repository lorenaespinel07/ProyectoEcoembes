package es.deusto.sd.ecoembes.entity;

import java.util.Date;

public class InfoContenedor {
	private long idContenedor;
	private int numeroEnvases;
	private NivelLlenado nivelLlenado;
	private Date fechaActu;
	
	
	public Date getFechaActu() {
		return fechaActu;
	}
	public void setFechaActu(Date fechaActu) {
		this.fechaActu = fechaActu;
	}
	
	public InfoContenedor(long idContenedor, int numeroEnvases, NivelLlenado nivelLlenado, Date fechaActu) {
		super();
		this.idContenedor = idContenedor;
		this.numeroEnvases = numeroEnvases;
		this.nivelLlenado = nivelLlenado;
		this.fechaActu = fechaActu;
	}
	public InfoContenedor() {}
	
	public long getIdContenedor() {
		return idContenedor;
	}
	public void setIdContenedor(long idContenedor) {
		this.idContenedor = idContenedor;
	}
	public int getNumeroEnvases() {
		return numeroEnvases;
	}
	public void setNumeroEnvases(int numeroEnvases) {
		this.numeroEnvases = numeroEnvases;
	}
	public NivelLlenado getNivelLlenado() {
		return nivelLlenado;
	}
	public void setNivelLlenado(NivelLlenado nivelLlenado) {
		this.nivelLlenado = nivelLlenado;
	}
	
	
}
