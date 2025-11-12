package es.deusto.sd.ecoembes.service;

import es.deusto.sd.ecoembes.entity.*;
import es.deusto.sd.ecoembes.dto.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class EcoService {
	private static Map<String, Personal> dbPersonal = new HashMap<>();
	private static ArrayList<Asignacion> dbAsignacion = new ArrayList<>();
	private static Map<String, Personal> dbTokensActivos = new HashMap<>();
	private static Map<Long, PlantaReciclaje> dbPlantas = new HashMap<>();
	private static Map<Long, List<InfoPlanta>> dbInfoPlanta = new HashMap<>();
	private static Map<Long, Contenedor> dbContenedor = new HashMap<>();
	private static Map<Long, List<InfoContenedor>> dbInfoContenedor = new HashMap<>();
	//Funciones para llenar la base de datos
	public void addPersonal(Personal p) {
		dbPersonal.put(p.getEmail(), p);
	}
	public void addAsignacion(Asignacion a) {
		dbAsignacion.add(a);
	}
	public void addTokenActivo(String token, Personal p) {
		dbTokensActivos.put(token, p);
	}
	public void addPlanta(PlantaReciclaje p) {
		dbPlantas.put(p.getIdplanta(), p);
	}
	public void addInfoPlanta(PlantaReciclaje p, ArrayList<InfoPlanta> lista) {
		dbInfoPlanta.put(p.getIdplanta(), lista);
	}
	public void addContenedor(Contenedor c) {
		dbContenedor.put(c.getId(), c);
	}
	public void addInfoContenedor(Contenedor c, ArrayList<InfoContenedor> lista) {
		dbInfoContenedor.put(c.getId(), lista);
	}
//	public Personal getPersonalById(long idpersonal) {
//		return dbPersonal.get(idpersonal);
//	}
//	public PlantaReciclaje getPlantaById(long idplanta) {
//		return dbPlantas.get(idplanta);
//	}
	// funciones de servicio
	
	/*
	 * POST /auth/login
	 * body:
	 * {
	 * 	"correo": "random@random.es",
	 * 	"contraseña": "random1234"
	 * }
	 * response: 202 OK (token creado)
	 * 
	 * POST /auth/logout
	 */
	public Optional<String> login(String correo, String contraseña) {
		Personal personal = dbPersonal.get(correo);
		if (personal != null && personal.getPassword().equals(contraseña)) {
			//Generar token por timestamp
			String token = generateToken();
			dbTokensActivos.put(token, personal);
			return Optional.of(token);
		}else {
			return Optional.empty();
		}
	}
	
	
	public Optional<Boolean> logout (String token) {
		if (dbTokensActivos.containsKey(token)) {
			dbTokensActivos.remove(token);
			return Optional.of(true);
		}else {
			return Optional.empty();
		}
	}
	public Optional<InfoContenedor> actualizarInfoContenedor(Long idContenedor, int numEnvases, String nivelLlenadoStr){
		Contenedor contenedor = dbContenedor.get(idContenedor);
		if (contenedor != null) {
			NivelLlenado nivelLlenado = NivelLlenado.valueOf(nivelLlenadoStr);
			InfoContenedor infoContenedor = new InfoContenedor(idContenedor, numEnvases, nivelLlenado,new Date());
			List<InfoContenedor> listaInfo = dbInfoContenedor.get(idContenedor);
			if (listaInfo == null) {
				listaInfo = new ArrayList<>();
				dbInfoContenedor.put(idContenedor, listaInfo);
			}
			listaInfo.add(infoContenedor);
			//Actualizar el nivel del contenedor
			contenedor.setNivel(nivelLlenado);
			return Optional.of(infoContenedor);
		} else {
			// El contenedor a actualizar no existe
			return Optional.empty();
		}
	}
	//POST
	public Optional<Contenedor> crearContenedor(long idConetendor, String ubicacion, String cp, int capacidadIni, String token){
		if (!validarToken(token)) {
			//token no valido
			return Optional.empty();
		}
		if (!dbContenedor.containsKey(idConetendor)) {
			Contenedor contenedor = new Contenedor(idConetendor, ubicacion, cp, capacidadIni);
			dbContenedor.put(idConetendor, contenedor);
			return Optional.of(contenedor);
		}else {
			//el conetendor ya existe 
			return Optional.empty();
		}
	}
	//GET 
	public Optional<ArrayList<InfoContenedor>> getInfoContendorPorFecha(long idContendor,Date fechaInicio, Date fechaFin, String token){
		if (!validarToken(token)) {
			//token no valido
			return Optional.empty();
		}
		Contenedor contenedor = dbContenedor.get(idContendor);
		if (contenedor != null) {
			List<InfoContenedor> listaInfo = dbInfoContenedor.get(idContendor);
			if (listaInfo != null) {
				ArrayList<InfoContenedor> resultado = new ArrayList<>();
				for (InfoContenedor info : listaInfo) {
					if (info.getFechaActu().after(fechaInicio) && info.getFechaActu().before(fechaFin)) {
						resultado.add(info);
					}
				}
				return Optional.of(resultado);
			} else {
				// No hay información para este contenedor
				return Optional.of(new ArrayList<>());
			}
		} else {
			// El contenedor no existe
			return Optional.empty();
		}
	}
	
	
	//GET
	public Optional< ArrayList<InfoContenedor>> getInfoContenedorPorZona(String cp, Date fecha, String token){
		if (!validarToken(token)) {
			//token no valido
			return Optional.empty();
		}
		//LocalDate fechaLocal = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ArrayList<InfoContenedor> resultado = new ArrayList<>();
		for (Contenedor contenedor : dbContenedor.values()) {
			if (contenedor.getCp().equals(cp)) {
				List<InfoContenedor> listaInfo = dbInfoContenedor.get(contenedor.getId());
				if (listaInfo != null) {
					for (InfoContenedor info : listaInfo) {
						//LocalDate fechaDia =  info.getFechaActu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						if (compraraDias(info.getFechaActu(), fecha)) {
							resultado.add(info);
						}
					}
				} else {
					return Optional.of(new ArrayList<>());
				}
			}
		}
		return Optional.of(resultado);
	}
	
	public Optional<ArrayList<InfoPlanta>> getInfoPlantasPorFecha(Date fecha, String token){
		if (!validarToken(token)) {
			//token no valido
			return Optional.empty();
		}
		ArrayList<InfoPlanta> resultado = new ArrayList<>();
		for (PlantaReciclaje planta : dbPlantas.values()) {
			List<InfoPlanta> listaInfo = dbInfoPlanta.get(planta.getIdplanta());
			if (listaInfo != null) {
				for (InfoPlanta info : listaInfo) {
					if (compraraDias(info.getFechaActu(), fecha)) {
						resultado.add(info);
					}
				}
			} else {
				return Optional.of(new ArrayList<>());
			}
		}
		return Optional.of(resultado);
	}
	
	public Optional<String> asignarContenedoresAPlantas(List<Long> idsContenedores, String token){
		if (!validarToken(token)) {
			//token no valido
			return Optional.empty();
		}
		ArrayList<Contenedor> contenedoresAsignados = new ArrayList<>();
		Personal personal = dbTokensActivos.get(token);
		PlantaReciclaje plantaAsignada = null;
		
		int envaseTotales = 0;
		//Lógica de asignación (simplificada)
		for (Long idContenedor : idsContenedores) {
			Contenedor contenedor = dbContenedor.get(idContenedor);
			if (contenedor != null) {
				//Asignar a la primera planta disponible (simplificado)
				InfoContenedor infoContendor = getUltimaInfoContenedor(idContenedor);
				if (infoContendor != null) {
					envaseTotales += infoContendor.getNumeroEnvases();
				}
			} else {
				return Optional.of("Contenedor con ID " + idContenedor + " no encontrado.");
			}
		}
		for(PlantaReciclaje planta : dbPlantas.values()) {
			List<InfoPlanta> listaInfo = dbInfoPlanta.get(planta.getIdplanta());
			if (listaInfo != null) {
				for(InfoPlanta plantaInfo : listaInfo) { 
					if (plantaInfo.getCapacidadActual() + envaseTotales <= plantaInfo.getPlanta().getCapacidad()) {
						//Asignación exitosa
						plantaAsignada = planta;
						Asignacion asignacion = new Asignacion(contenedoresAsignados, plantaAsignada, personal, new Date());
						dbAsignacion.add(asignacion);
						return Optional.of("Contenedores asignados a la planta " + plantaInfo.getPlanta().getNombre() + " cantidad estimada de envases: "+ envaseTotales);
					}
				}
			}
			return Optional.of("No hay información de capacidad para la planta: " + planta.getNombre());
		}
		return Optional.of("No hay plantas con capacidad suficiente para los envases totales: " + envaseTotales);
	}
	private static synchronized InfoContenedor getUltimaInfoContenedor(Long idContenedor) {
		List<InfoContenedor> listaInfo = dbInfoContenedor.get(idContenedor);
		if (listaInfo != null && !listaInfo.isEmpty()) {
			return listaInfo.get(listaInfo.size() - 1);
		}
		return null;
	}
	private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
	
	
	private static synchronized boolean compraraDias(Date fecha1, Date fecha2) {
		LocalDate localDate1 = fecha1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = fecha2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.equals(localDate2);
	}
	
	private static synchronized boolean validarToken(String token) {
		return dbTokensActivos.containsKey(token);
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
