package ws.beauty.salon.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ws.beauty.salon.dto.AttendanceLogRequest;
import ws.beauty.salon.dto.AttendanceLogResponse;
import ws.beauty.salon.service.AttendanceLogService;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceLogController {
     private final AttendanceLogService service;


// Obtiene registros de asistencia de forma paginada.
@GetMapping(value = "/pagination", params = { "page", "pageSize" })
public List<AttendanceLogResponse> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize) {
    if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
        throw new IllegalArgumentException("Invalid pagination parameters.");
    }
    return service.findAll(page, pageSize);
}

// Busca un registro de asistencia por su ID.
@GetMapping("/{id}")
public AttendanceLogResponse findById(@PathVariable Integer id) {
    return service.findById(id);
}

// Crea un nuevo registro de asistencia (check-in).
@PostMapping
public ResponseEntity<AttendanceLogResponse> create(@Valid @RequestBody AttendanceLogRequest request) {
    AttendanceLogResponse created = service.create(request);
    return ResponseEntity.created(URI.create("/api/v1/attendance/" + created.getId())).body(created);
}

// Actualiza un registro de asistencia existente.
@PutMapping("/{id}")
public AttendanceLogResponse update(@PathVariable Integer id, @Valid @RequestBody AttendanceLogRequest request) {
    return service.update(id, request);
}


// Obtiene todos los registros de asistencia de un estilista.
@GetMapping("/stylist/{stylistId}")
public List<AttendanceLogResponse> findByStylist(@PathVariable Integer stylistId) {
    return service.findByStylistId(stylistId);
}

// Busca registros de asistencia dentro de un rango de fechas.
@GetMapping("/range")
public List<AttendanceLogResponse> findByDateRange(
        @RequestParam LocalDateTime start,
        @RequestParam LocalDateTime end) {
    return service.findByCheckInBetween(start, end);
}

// Verifica si un estilista tiene una asistencia abierta (sin check-out).
@GetMapping("/open/{stylistId}")
public boolean hasOpenAttendance(@PathVariable Integer stylistId) {
    return service.hasOpenAttendance(stylistId);
}

// Cierra la asistencia abierta de un estilista (check-out).
@PutMapping("/close/{stylistId}")
public AttendanceLogResponse closeAttendance(@PathVariable Integer stylistId) {
    return service.closeAttendance(stylistId);
}
}
