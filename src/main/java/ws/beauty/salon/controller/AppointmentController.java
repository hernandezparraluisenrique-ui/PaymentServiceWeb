package ws.beauty.salon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ws.beauty.salon.dto.AppointmentRequest;
import ws.beauty.salon.dto.AppointmentResponse;
import ws.beauty.salon.service.AppointmentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public List<AppointmentResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{idAppointment}")
    public AppointmentResponse getById(@PathVariable Long idAppointment) {
        return service.findById(idAppointment);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest req) {
        AppointmentResponse created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/v1/appointments/" + created.getIdAppointment()))
                .body(created);
    }

    @PutMapping("/{idAppointment}")
    public AppointmentResponse update(@PathVariable Long idAppointment, @Valid @RequestBody AppointmentRequest req) {
        return service.update(idAppointment, req);
    }

    @DeleteMapping("/{idAppointment}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idAppointment) {
        service.delete(idAppointment);
    }
}
