package es.deusto.sd.ecoembes.facade;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import es.deusto.sd.ecoembes.service.AuthService;
import es.deusto.sd.ecoembes.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.ecoembes.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/eco/planta")
@Tag(name = "Planta Controller", description = "Operaciones de Plantas y Asignaciones")
public class PlantaController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PlantaService plantaService;

    @Operation(summary = "Obtener capacidad de plantas por fecha", description = "Permite obtener la capacidad de las plantas en una fecha específica utilizando un token.", responses = {
            @ApiResponse(responseCode = "200", description = "OK: Capacidad de las plantas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para la consulta"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para consultar información") })


    @GetMapping("/capacidad")
    public ResponseEntity<?> getPlantasPorFecha(
            @Parameter(description = "Fecha de las plantas a consultar", required = true)
            @RequestParam("Fecha") String Fecha,
            @Parameter(description = "Token de autorización", required = true)
            @RequestParam("token") String token
    ) {
        String textoFecha = Fecha;
        LocalDate fechaLocal = LocalDate.parse(textoFecha);
        System.out.println("Fecha parseada: " + fechaLocal.getYear() + "-" + fechaLocal.getMonthValue() + "-" + fechaLocal.getDayOfMonth());
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(fechaLocal.getYear(), fechaLocal.getMonthValue()-1, fechaLocal.getDayOfMonth(), 3, 00);
        System.out.println(Fecha+"Fecha consultada: " + cal.getTime());
        Optional<?> plantas = plantaService.getInfoPlantasPorFecha(cal.getTime(), token);
        if (plantas.isPresent()) {
            if (plantas.get() instanceof String) {
                return new ResponseEntity<>((String) plantas.get(), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(plantas.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Asignar contenedores a planta de reciclaje", description = "Permite asignar una lista de contenedores a una planta de reciclaje utilizando un token.", responses = {
            @ApiResponse(responseCode = "201", description = "Created: Contenedores asignados correctamente a la planta de reciclaje"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos para la asignación"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Token inválido, no autorizado para asignar contenedores") })

    @PostMapping("/asignacion")
    public ResponseEntity<?> asignarPlantaReciclaje(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la asignación de planta", required = true)
            @RequestBody AsignacionDTO asignacionDTO
    ) {

        Optional<?> asignacion = plantaService.asignarContenedoresAPlantas(asignacionDTO.getIdContenedores(),asignacionDTO.getToken());

        if (asignacion.isPresent()) {
            if (asignacion.get().equals("UNAUTHORIZED")) {
                return new ResponseEntity<>((String) asignacion.get(), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(asignacion.get(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }}
