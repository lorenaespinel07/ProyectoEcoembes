package es.deusto.sd.ecoembes.facade;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.ecoembes.dto.*;
import es.deusto.sd.ecoembes.entity.*;
import es.deusto.sd.ecoembes.service.EcoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/eco")
@Tag(name = "Eco Controller", description = "Operaciones de Ecoembes")
public class EcoController {
	private EcoService ecoService;
	
	public EcoController(EcoService ecoService) {
		this.ecoService = ecoService;
	}
	
	//Login
	@Operation(
		summary = "login al sistema",
		description = "Permite a un usuario iniciar sesión proporcionando correo electrónico y contraseña. Devuelve un token si tiene éxito.",
		responses = {
			@ApiResponse(responseCode = "200", description = "OK: Inicio de sesión exitoso, devuelve un token"),
			@ApiResponse(responseCode = "401", description = "No autorizado: Credenciales inválidas, inicio de sesión fallido"),
				
		}
	)
	@PostMapping("/auth/login")
	public ResponseEntity<String> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciales del usuario", required = true)
			@RequestBody CredentialsDTO credentials) {
		Optional<String> token = ecoService.login(credentials.getEmail(), credentials.getPassword());
		if (token.isPresent()) {
			return new ResponseEntity<>(token.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	// Logout endpoint
    @Operation(
        summary = "logout del sistema",
        description = "Permite a un usuario cerrar sesión proporcionando el token de autorización.",
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content: Cierre de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, cierre de sesión fallido"),
        }
    )    
    @PostMapping("/auth/logout")    
    public ResponseEntity<Void> logout(
    		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Token a invalidar en texto plano", required = true)
    		@RequestBody LogoutDTO logoutDTO) {    	
        Optional<Boolean> result = ecoService.logout(logoutDTO.getToken());
    	
        if (result.isPresent() && result.get()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
        } else {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
	
    
    @Operation(
    	summary = "Actualizar información del contenedor",
    	description = "Permite actualizar la información de un contenedor específico mediante su sensor",
    	responses = {
    		@ApiResponse(responseCode = "200", description = "OK: Información del contenedor actualizada correctamente"),
			@ApiResponse(responseCode = "404", description = "Not Found: Contenedor no encontrado"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error: Error interno del servidor")
    	}
    )
    @PostMapping("/sensores/actualizar")
	public ResponseEntity<InfoContenedorDTO> actualizarInfoContenedor(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del contenedor a actualizar", required = true)
		@RequestBody InfoContenedorDTO infoContenedorDTO){
    	Optional<InfoContenedor> infoContenedor = ecoService.actualizarInfoContenedor(
		infoContenedorDTO.getIdContendor(),
		infoContenedorDTO.getNumEstimado(),
		infoContenedorDTO.getNivelLlenado()
		);
    	if (infoContenedor.isPresent()) {
			return new ResponseEntity<>(infoContenedorDTO ,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
    @Operation(
    	summary = "Crear nuevo contenedor",
    	description = "Permite crear un nuevo contenedor en el sistema con un token.",
    	responses = {
    		@ApiResponse(responseCode = "201", description = "Created: Contenedor creado correctamente"),
    		@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para crear el contenedor"), 	
    		@ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para crear contenedor")
    	}
    )
	@PostMapping("/contenedores")
	public ResponseEntity<?> crearContenedor(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del contenedor a crear", required = true)
		@RequestBody ContenedorDTO contenedorDTO){
    	
		Optional<?> contenedor = ecoService.crearContenedor(contenedorDTO.getIdContenedor(),
				contenedorDTO.getDireccion(), contenedorDTO.getCp(), contenedorDTO.getCapacidadIni(),
				contenedorDTO.getToken());
		
		if (contenedor.isPresent()) {
			if (contenedor.get() instanceof String) {
				return new ResponseEntity<>((String) contenedor.get(), HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(contenedor.get(), HttpStatus.CREATED);
			
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} 	
	}
    
	@Operation(summary = "Obtener información del contenedor por fecha", description = "Permite obtener la información de un contenedor específico en un periodo de tiempo determinado utilizando su ID y un token.", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Información del contenedor obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para la consulta"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para consultar información") })
    
	@GetMapping("/contenedores/{id}/info")
	public ResponseEntity<?> getInfoContenedorPorFecha(
		@Parameter(description = "ID del contenedor a consultar", required = true)
		@PathVariable("id") long id,
		@Parameter(description = "Rango de fechas para la consulta", required = true)
	    @RequestParam("FechaIni") String FechaIni,
	    @RequestParam("FechaFin") String FechaFin,
	    @Parameter(description = "Token de autorización", required = true)
		@RequestParam("token") String token
		){
		
		String textoFechaIni = FechaIni;
		String textoFechaFin = FechaFin;
		
		LocalDate fechaIni = LocalDate.parse(textoFechaIni);
		LocalDate fechaFin = LocalDate.parse(textoFechaFin);
		
		Calendar calIni = Calendar.getInstance();
		calIni.set(fechaIni.getYear(), fechaIni.getMonthValue(), fechaIni.getDayOfMonth());
		Calendar calFin = Calendar.getInstance();
		calFin.set(fechaFin.getYear(), fechaFin.getMonthValue(), fechaFin.getDayOfMonth());
		
		Optional<?> infoContenedor = ecoService.getInfoContendorPorFecha(id,
				calIni.getTime(), calFin.getTime(), token);
		if (infoContenedor.isPresent()) {
			if (infoContenedor.get() instanceof String) {
				return new ResponseEntity<>((String) infoContenedor.get(), HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(infoContenedor.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Obtener información del contenedor por zona", description = "Permite obtener la información de los contenedores en una zona específica utilizando el código postal y un token.", responses = {
			@ApiResponse(responseCode = "200", description = "OK: Información del contenedor obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para la consulta"),	
			@ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para consultar información") })
	
	@GetMapping("/{cp}/contenedores")
	public ResponseEntity<?> getInfoContenedorPorZona(
			@Parameter(description = "Código postal de la zona a consultar", required = true) 
			@PathVariable("cp") String cp,
			@Parameter(description = "Fecha para la consulta", required = true) 
			@RequestParam("fecha") String fecha,
			@Parameter(description = "Token de autorización", required = true) 
			@RequestParam("token") String token
			) {

		String textoFecha = fecha;

		LocalDate fechaLocal = LocalDate.parse(textoFecha);

		Calendar cal = Calendar.getInstance();
		cal.set(fechaLocal.getYear(), fechaLocal.getMonthValue(), fechaLocal.getDayOfMonth());

		Optional<?> infoContenedor = ecoService.getInfoContenedorPorZona(cp, cal.getTime(),token);
		
		if (infoContenedor.isPresent()) {
			if (infoContenedor.get() instanceof String) {
				return new ResponseEntity<>((String) infoContenedor.get(), HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(infoContenedor.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Operation(summary = "Obtener capacidad de plantas por fecha", description = "Permite obtener la capacidad de las plantas en una fecha específica utilizando un token.", responses = {
            @ApiResponse(responseCode = "200", description = "OK: Capacidad de las plantas obtenida correctamente"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para la consulta"),	
            @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para consultar información") })
	
	
	@GetMapping("/plantas/capacidad")
	public ResponseEntity<?> getPlantasPorFecha(
			@Parameter(description = "Fecha de las plantas a consultar", required = true) 
			@RequestParam("Fecha") String Fecha,
			@Parameter(description = "Token de autorización", required = true) 
			@RequestParam("token") String token
			) {
		String textoFecha = Fecha;
		LocalDate fechaLocal = LocalDate.parse(textoFecha);
		Calendar cal = Calendar.getInstance();
		cal.set(fechaLocal.getYear(), fechaLocal.getMonthValue(), fechaLocal.getDayOfMonth());
		Optional<?> plantas = ecoService.getInfoPlantasPorFecha(cal.getTime(), token);
		if (plantas.isPresent()) {
			if (plantas.get() instanceof String) {
				return new ResponseEntity<>((String) plantas.get(), HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(plantas.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Operation(summary = "Asignar contenedores a planta de reciclaje", description = "Permite asignar una lista de contenedores a una planta de reciclaje utilizando un token.", responses = {
			@ApiResponse(responseCode = "201", description = "Created: Contenedores asignados correctamente a la planta de reciclaje"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para la asignación"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para asignar contenedores") })
	
	@PostMapping("/asignaciones")
	public ResponseEntity<?> asignarPlantaReciclaje(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la asignación de planta", required = true) 
			@RequestBody AsignacionDTO asignacionDTO
			) {
		
		Optional<?> asignacion = ecoService.asignarContenedoresAPlantas(asignacionDTO.getIdContenedores(),asignacionDTO.getToken());

		if (asignacion.isPresent()) {			
			if (asignacion.get().equals("UNAUTHORIZED")) {
				return new ResponseEntity<>((String) asignacion.get(), HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(asignacion.get(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
