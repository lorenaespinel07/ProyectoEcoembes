package es.deusto.sd.ecoembes.facade;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.ecoembes.dto.*;
import es.deusto.sd.ecoembes.entity.InfoContenedor;
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
    		@RequestBody String token) {    	
        Optional<Boolean> result = ecoService.logout(token);
    	
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
    		@ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para crear el contenedor") 		
    	}
    )
	@PostMapping("/contenedores")
	public ResponseEntity<?> crearContenedor(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del contenedor a crear", required = true)
		@RequestBody ContenedorDTO contenedorDTO){
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
