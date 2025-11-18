package ws.beauty.salon.controller;

import java.net.URI;
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

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ws.beauty.salon.dto.ServiceRequest;
import ws.beauty.salon.dto.ServiceResponse;
import ws.beauty.salon.service.ServiceService;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService service;

    // Obtiene todos los servicios utilizando paginación. Valida que los parámetros no sean negativos.
@GetMapping(value = "/pagination", params = { "page", "pageSize" })
@Operation(summary = "Get all services with pagination")
public List<ServiceResponse> findAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

    if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
        throw new IllegalArgumentException("Invalid pagination parameters.");
    }
    return service.findAll(page, pageSize);
}

// Busca un servicio por su ID y lo devuelve. Lanza error si no existe.
@GetMapping("/{id}")
@Operation(summary = "Get service by ID")
public ServiceResponse findById(@PathVariable Integer id) {
    return service.findById(id);
}

// Crea un nuevo servicio y devuelve la ubicación del recurso creado.
@PostMapping
@Operation(summary = "Create new service")
public ResponseEntity<ServiceResponse> create(@Valid @RequestBody ServiceRequest req) {
    ServiceResponse created = service.create(req);
    return ResponseEntity.created(URI.create("/api/v1/services/" + created.getId())).body(created);
}

// Actualiza un servicio existente mediante su ID.
@PutMapping("/{id}")
@Operation(summary = "Update existing service")
public ServiceResponse update(@PathVariable Integer id, @Valid @RequestBody ServiceRequest req) {
    return service.update(id, req);
}

// Obtiene una lista de servicios filtrados por coincidencia parcial del nombre.
@GetMapping("/name/{name}")
@Operation(summary = "Get services by name")
public List<ServiceResponse> findByServiceName(@PathVariable String name) {
    return service.findByServiceName(name);
}

// Devuelve todos los servicios asociados a una categoría específica.
@GetMapping("/category/{categoryId}")
@Operation(summary = "Get services by category ID")
public List<ServiceResponse> findByCategoryId(@PathVariable Integer categoryId) {
    return service.findByCategoryId(categoryId);
}
}
