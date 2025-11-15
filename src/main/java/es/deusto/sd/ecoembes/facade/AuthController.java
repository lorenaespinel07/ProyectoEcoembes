package es.deusto.sd.ecoembes.facade;

import es.deusto.sd.ecoembes.dto.*;
import es.deusto.sd.ecoembes.service.EcoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/eco/auth")
@Tag(name = "Auth Controller", description = "Operaciones de Autenticación")

public class AuthController {
    private final EcoService ecoService;

    public AuthController(EcoService authService) {
        this.ecoService = authService;
    }

    @Operation(
            summary = "login al sistema",
            description = "Permite a un usuario iniciar sesión proporcionando correo electrónico y contraseña. Devuelve un token si tiene éxito.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK: Inicio de sesión exitoso, devuelve un token"),
                    @ApiResponse(responseCode = "401", description = "No autorizado: Credenciales inválidas, inicio de sesión fallido"),

            }
    )

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciales del usuario", required = true)
            @RequestBody LoginDTO loginDTO) {
        Optional<String> token = ecoService.login(loginDTO.getCorreo(), loginDTO.getContrasena());
        if (token.isPresent()) {
            return new ResponseEntity<>(token.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/logout")
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
}
