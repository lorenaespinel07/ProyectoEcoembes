package es.deusto.sd.ecoembes.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import es.deusto.sd.ecoembes.entity.*;

public class Ruta {
	
	private long idRuta;
	private String matricula;
	private ArrayList<Contenedor> contenedores;
	private Date fecha;
	private Double distanciaTotal;
	private Double duracionEstimada;

	// Constructor
    public Ruta(long idRuta, String matricula, Date fecha, Double distanciaTotal, Double duracionEstimada) {
        this.idRuta = idRuta;
        this.matricula = matricula;
        this.fecha = fecha;
        this.distanciaTotal = distanciaTotal;
        this.duracionEstimada = duracionEstimada;
        this.contenedores = new ArrayList<>();
    }

    // Getters y setters
    public long getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(long idRuta) {
        this.idRuta = idRuta;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public ArrayList<Contenedor> getContenedores() {
        return contenedores;
    }

    public void setContenedores(ArrayList<Contenedor> contenedores) {
        this.contenedores = contenedores;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(Double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public Double getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(Double duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    // Métodos
    public void calcularRutaOptima() {  
    	
    }

    /*
    public Double obtenerDuracionEstimada() {
    	
    }
     * 
     */

    public void agregarContenedor(Contenedor contenedor) {
      
    }

    public void eliminarContenedor(Contenedor contenedor) {
       
    }
}