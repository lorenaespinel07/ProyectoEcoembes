package es.deusto.sd.ecoembes.entity;

import java.util.Date;
import java.util.Objects;

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
	@Column(nullable = false)
	private long idContenedor;
	
	@Column(nullable = false)
	private int numeroEnvases;
	@Enumerated(EnumType.STRING)
	private NivelLlenado nivelLlenado;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActu;

    @ManyToOne
    @JoinColumn(name = "contenedor_id")
    private Contenedor contenedor;



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
	@Override
	public int hashCode() {
		return Objects.hash(fechaActu, idContenedor, nivelLlenado, numeroEnvases);
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
		return Objects.equals(fechaActu, other.fechaActu) && idContenedor == other.idContenedor
				&& nivelLlenado == other.nivelLlenado && numeroEnvases == other.numeroEnvases;
	}


    public void setContenedor(Contenedor c) {
        this.contenedor = c;
    }

    public Contenedor getContenedor() {
        return this.contenedor;
    }
}
