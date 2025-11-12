package es.deusto.sd.ecoembes.dto;

public class ContenedorDTO {
	int idContenedor;
	private class Ubicacion {
		String direccion;
		String cp;
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getCp() {
			return cp;
		}
		public void setCp(String cp) {
			this.cp = cp;
		}
	}
	Ubicacion ubicacion;
	int capacidadIni;
	public int getIdContenedor() {
		return idContenedor;
	}
	public void setIdContenedor(int idContenedor) {
		this.idContenedor = idContenedor;
	}
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getCapacidadIni() {
		return capacidadIni;
	}
	public void setCapacidadIni(int capacidadIni) {
		this.capacidadIni = capacidadIni;
	}
}
