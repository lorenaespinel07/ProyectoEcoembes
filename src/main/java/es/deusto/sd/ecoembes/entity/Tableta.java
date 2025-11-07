package es.deusto.sd.ecoembes.entity;

import es.deusto.sd.ecoembes.entity.Estado;

import java.util.Objects;

public class Tableta {
	
	private long idTableta;                 
    private String matriculaCamion;
    private long idRuta;
    private Date fechaRuta;
    private double distanciaTotal;
    private double duracionEstimada;
    private List<String> ubicacionesContenedores;

    // Constructor
    public TabletaDTO(long idTableta, String matriculaCamion, long idRuta, Date fechaRuta,
                      double distanciaTotal, double duracionEstimada,
                      List<String> ubicacionesContenedores) {
        this.idTableta = idTableta;
        this.matriculaCamion = matriculaCamion;
        this.idRuta = idRuta;
        this.fechaRuta = fechaRuta;
        this.distanciaTotal = distanciaTotal;
        this.duracionEstimada = duracionEstimada;
        this.ubicacionesContenedores = ubicacionesContenedores;
    }

    // Getters y Setters
    public long getIdTableta() {
        return idTableta;
    }

    public void setIdTableta(long idTableta) {
        this.idTableta = idTableta;
    }

    public String getMatriculaCamion() {
        return matriculaCamion;
    }

    public void setMatriculaCamion(String matriculaCamion) {
        this.matriculaCamion = matriculaCamion;
    }

    public long getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(long idRuta) {
        this.idRuta = idRuta;
    }

    public Date getFechaRuta() {
        return fechaRuta;
    }

    public void setFechaRuta(Date fechaRuta) {
        this.fechaRuta = fechaRuta;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public double getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(double duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public List<String> getUbicacionesContenedores() {
        return ubicacionesContenedores;
    }

    public void setUbicacionesContenedores(List<String> ubicacionesContenedores) {
        this.ubicacionesContenedores = ubicacionesContenedores;
    }

}