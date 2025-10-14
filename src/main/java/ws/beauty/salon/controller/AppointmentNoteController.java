package ws.beauty.salon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ws.beauty.salon.dto.AppointmentNoteRequest;
import ws.beauty.salon.dto.AppointmentNoteResponse;
import ws.beauty.salon.service.AppointmentNoteService;

import java.util.List;

@RestController
@RequestMapping("/api/appointment-notes")
@RequiredArgsConstructor
@Tag(
    name = "Appointment Notes",
    description = "API para gestionar las notas asociadas a las citas (appointments). " +
                  "Permite crear, leer, actualizar y eliminar notas."
)
public class AppointmentNoteController {

    private final AppointmentNoteService noteService;

    // ───────────────────────────────────────────────────────────────
    @Operation(
        summary = "Obtener todas las notas de citas",
        description = "Devuelve una lista con todas las notas registradas sin paginación.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentNoteResponse.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<AppointmentNoteResponse>> getAll() {
        return ResponseEntity.ok(noteService.findAll());
    }

    // ───────────────────────────────────────────────────────────────
    @Operation(
        summary = "Obtener notas con paginación",
        description = "Permite obtener las notas de citas de manera paginada usando parámetros `page` y `size`.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Notas paginadas obtenidas correctamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentNoteResponse.class)))
        }
    )
    @GetMapping("/paginated")
    public ResponseEntity<List<AppointmentNoteResponse>> getAllPaginated(
            @Parameter(description = "Número de página (inicia en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(noteService.findAllPaginated(page, size));
    }

    // ───────────────────────────────────────────────────────────────
    @Operation(
        summary = "Buscar una nota por ID",
        description = "Obtiene una nota específica según su ID único.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Nota encontrada",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentNoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentNoteResponse> getById(
            @Parameter(description = "ID de la nota", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    // ───────────────────────────────────────────────────────────────
    @Operation(
        summary = "Crear una nueva nota de cita",
        description = "Registra una nueva nota asociada a una cita existente.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Datos de la nueva nota",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de creación de nota",
                    value = """
                        {
                            "idAppointment": 2,
                            "noteText": "El cliente llegó 10 minutos antes de la cita y solicitó corte clásico."
                        }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Nota creada exitosamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentNoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
        }
    )
    @PostMapping
    public ResponseEntity<AppointmentNoteResponse> create(@RequestBody AppointmentNoteRequest request) {
        return ResponseEntity.ok(noteService.create(request));
    }

    // ───────────────────────────────────────────────────────────────
    @Operation(
        summary = "Actualizar una nota existente",
        description = "Actualiza los datos de una nota ya registrada mediante su ID.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Nuevos datos de la nota",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de actualización de nota",
                    value = """
                        {
                            "idAppointment": 2,
                            "noteText": "El cliente pidió cambiar el peinado al final del servicio."
                        }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Nota actualizada correctamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppointmentNoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nota o cita no encontrada")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentNoteResponse> update(
            @Parameter(description = "ID de la nota a actualizar", example = "3") @PathVariable Integer id,
            @RequestBody AppointmentNoteRequest request) {
        return ResponseEntity.ok(noteService.update(id, request));
    }

    // ───────────────────────────────────────────────────────────────
    @Operation(
        summary = "Eliminar una nota por ID",
        description = "Elimina una nota específica de la base de datos.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Nota eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la nota a eliminar", example = "5") @PathVariable Integer id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
