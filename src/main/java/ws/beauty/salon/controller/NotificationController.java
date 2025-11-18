package ws.beauty.salon.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ws.beauty.salon.dto.NotificationRequest;
import ws.beauty.salon.dto.NotificationResponse;
import ws.beauty.salon.service.NotificationService;



@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor


public class NotificationController {

     private final NotificationService service;

    // 🔹 Obtener todas las notificaciones con paginación
    @GetMapping(params = { "page", "pageSize" })
    @Operation(summary = "Get all notifications with pagination")
    @ApiResponse(responseCode = "200", description = "Paginated list of notifications.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = NotificationResponse.class))) })
    public List<NotificationResponse> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }
        return service.findAll(page, pageSize);
    }

    // 🔹 Obtener una notificación por su ID
    @GetMapping("/{idNotification}")
    @Operation(summary = "Get notification by ID")
    public NotificationResponse findById(@PathVariable Integer idNotification) {
        return service.findById(idNotification);
    }

    // 🔹 Crear una nueva notificación
    @PostMapping
    @Operation(summary = "Create new notification")
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest req) {
        NotificationResponse created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/v1/notifications/" + created.getId()))
                .body(created);
    }

    // 🔹 Actualizar una notificación existente
    @PutMapping("/{idNotification}")
    @Operation(summary = "Update notification by ID")
    public NotificationResponse update(
            @PathVariable Integer idNotification,
            @Valid @RequestBody NotificationRequest req) {
        return service.update(idNotification, req);
    }


    // 🔹 Buscar notificaciones por cliente
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get notifications by client ID")
    public List<NotificationResponse> findByClientId(@PathVariable Integer clientId) {
        return service.findByClientId(clientId);
    }

    // 🔹 Buscar notificaciones por medio de envío
    @GetMapping("/sent-via/{sentVia}")
    @Operation(summary = "Get notifications by sent via method")
    public List<NotificationResponse> findBySentVia(@PathVariable String sentVia) {
        return service.findBySentVia(sentVia);
    }

    // 🔹 Buscar notificaciones en un rango de fechas
    @GetMapping("/dates")
    @Operation(summary = "Get notifications between two dates")
    public List<NotificationResponse> findBySentAtBetween(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return service.findBySentAtBetween(start, end);
    }

    // 🔹 Contar notificaciones por cliente
    @GetMapping("/count/client/{clientId}")
    @Operation(summary = "Count notifications by client ID")
    public long countByClientId(@PathVariable Integer clientId) {
        return service.countByClientId(clientId);
    }
    
}
