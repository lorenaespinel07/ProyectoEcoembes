package es.deusto.sd.ecoembes.facade;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "Planta Controller", description = "Operaciones de Plantas y Asignaciones")
public class PlantaController {
    private final EcoService ecoService; // Inyecta el Facade

    public PlantaController(EcoService ecoService) {
        this.ecoService = ecoService;
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
