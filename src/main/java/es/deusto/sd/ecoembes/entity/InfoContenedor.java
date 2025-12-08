package es.deusto.sd.ecoembes.entity;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
@Entity
public class InfoContenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    //Creo que hay que cambiar esta logica
	//Para mi yo del futuro:
	//Igual quieres que en vez del id se alamacene 
	//la referencia al objeto
    @ManyToOne
    @JoinColumn(name = "id_contenedor", nullable = false)
    @JsonIgnore
    private Contenedor contenedor;
	
	@Column(nullable = false)
	private int numeroEnvases;
	@Enumerated(EnumType.STRING)
	private NivelLlenado nivelLlenado;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActu;





    public Date getFechaActu() {
		return fechaActu;
	}
	public void setFechaActu(Date fechaActu) {
		this.fechaActu = fechaActu;
	}
	
	public InfoContenedor(Contenedor contenedor, int numeroEnvases, NivelLlenado nivelLlenado, Date fechaActu) {
		super();
        this.contenedor = contenedor;
		this.numeroEnvases = numeroEnvases;
		this.nivelLlenado = nivelLlenado;
		this.fechaActu = fechaActu;
	}
	public InfoContenedor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdContenedor() {
		return contenedor.getId();
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
	@Override
	public int hashCode() {
		return Objects.hash(fechaActu, contenedor.getId(), nivelLlenado, numeroEnvases);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoContenedor other = (InfoContenedor) obj;
		return Objects.equals(fechaActu, other.fechaActu) && contenedor.getId() == other.contenedor.getId()
				&& nivelLlenado == other.nivelLlenado && numeroEnvases == other.numeroEnvases;
	}


    public void setContenedor(Contenedor c) {
        this.contenedor = c;
    }

    public Contenedor getContenedor() {
        return this.contenedor;
    }
}
