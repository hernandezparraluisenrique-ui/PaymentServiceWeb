package ws.beauty.salon.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import ws.beauty.salon.dto.AppointmentRequestDTO;
import ws.beauty.salon.model.Appointment;
import ws.beauty.salon.service.AppointmentService;

@RestController
@RequestMapping("appointments")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Appointments", description = "Provides methods for managing appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all appointments")
    @GetMapping
    public List<Appointment> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get all appointments with pagination")
    @GetMapping(value = "pagination", params = { "page", "pageSize" })
    public List<Appointment> getAllPaginated(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        return service.getAll(page, pageSize);
    }

    @Operation(summary = "Get appointments ordered by date")
    @GetMapping("orderByDate")
    public List<Appointment> getAllOrderByDate() {
        return service.getAllOrderByDate();
    }

    @Operation(summary = "Get an appointment by its ID")
    @GetMapping("{idAppointment}")
    public ResponseEntity<Appointment> getById(@PathVariable Integer idAppointment) {
        Appointment appointment = service.getById(idAppointment);
        return (appointment != null)
                ? new ResponseEntity<>(appointment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get appointments by status")
    @GetMapping("status/{status}")
    public List<Appointment> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @Operation(summary = "Get appointments by client ID")
    @GetMapping("client/{clientId}")
    public List<Appointment> getByClient(@PathVariable Integer clientId) {
        return service.getByClient(clientId);
    }

    @Operation(summary = "Create a new appointment")
    @PostMapping
    public ResponseEntity<AppointmentRequestDTO> add(@RequestBody AppointmentRequestDTO appointmentDTO) {
        Appointment saved = service.save(convertToEntity(appointmentDTO));
        return new ResponseEntity<>(convertToDTO(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an appointment")
    @PutMapping("{idAppointment}")
    public ResponseEntity<AppointmentRequestDTO> update(@PathVariable Integer idAppointment,
                                                        @RequestBody AppointmentRequestDTO appointmentDTO) {
        Appointment existing = service.getById(idAppointment);
        if (existing == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Appointment updated = convertToEntity(appointmentDTO);
        updated.setId(idAppointment);
        service.save(updated);
        return new ResponseEntity<>(convertToDTO(updated), HttpStatus.OK);
    }

    @Operation(summary = "Delete an appointment")
    @DeleteMapping("{idAppointment}")
    public ResponseEntity<Void> delete(@PathVariable Integer idAppointment) {
        service.delete(idAppointment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AppointmentRequestDTO convertToDTO(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentRequestDTO.class);
    }

    private Appointment convertToEntity(AppointmentRequestDTO dto) {
        return modelMapper.map(dto, Appointment.class);
    }
}
