package es.deusto.sd.ecoembes.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class InfoPlanta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    //Aqui hay dos logicas
	//1. InfoContendor contiene el id del contenedor referenciado
	//2. InfoPlanta contiene el objeto planta completo
	@ManyToOne
	@JoinColumn(name = "planta_id", nullable = false)
    @JsonIgnore
	private PlantaReciclaje planta;
	@Column(nullable = false)
	private double capacidadActual;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActu;

    public Long getPlantaId() {
        if (this.planta != null) {
            return this.planta.getIdplanta();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlantaReciclaje getPlanta() {
		return planta;
	}
	public void setPlanta(PlantaReciclaje planta) {
		this.planta = planta;
	}
	public double getCapacidadActual() {
		return capacidadActual;
	}
	public void setCapacidadActual(double capacidadActual) {
		this.capacidadActual = capacidadActual;
	}
	public Date getFechaActu() {
		return fechaActu;
	}
	public void setFechaActu(Date fechaActu) {
		this.fechaActu = fechaActu;
	}
	public InfoPlanta(PlantaReciclaje planta, double capacidadActual, Date fechaActu) {
		super();
		this.planta = planta;
		this.capacidadActual = capacidadActual;
		this.fechaActu = fechaActu;
	}
	public InfoPlanta() {}
}
