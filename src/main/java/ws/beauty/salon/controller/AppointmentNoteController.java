package ws.beauty.salon.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ws.beauty.salon.dto.AppointmentNoteRequestDTO;
import ws.beauty.salon.model.AppointmentNote;
import ws.beauty.salon.service.AppointmentNoteService;

@RestController
@RequestMapping("appointment-notes")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "AppointmentNotes", description = "Provides methods for managing appointment notes")
public class AppointmentNoteController {

    @Autowired
    private AppointmentNoteService service;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las notas
    @GetMapping
    public List<AppointmentNote> getAll() {
        return service.getAll();
    }

    // Obtener nota por ID
    @GetMapping("{idNote}")
    public ResponseEntity<AppointmentNote> getById(@PathVariable Integer idNote) {
        Optional<AppointmentNote> note = service.getById(idNote);
        return note.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener notas por cita
    @GetMapping("/appointment/{appointmentId}")
    public List<AppointmentNote> getByAppointment(@PathVariable Integer appointmentId) {
        return service.getByAppointment(appointmentId);
    }

    // Crear o actualizar nota
    /*@PostMapping
    public ResponseEntity<AppointmentNoteRequestDTO> add(@RequestBody AppointmentNoteRequestDTO noteDTO) {
        AppointmentNote note = convertToEntity(noteDTO);
        note = service.assignAppointment(note, noteDTO.getAppointmentId());
        AppointmentNote savedNote = service.save(note);
        return new ResponseEntity<>(convertToDTO(savedNote), HttpStatus.CREATED);
    }*/

    // Eliminar nota
    /*@DeleteMapping("{idNote}")
    public ResponseEntity<Void> delete(@PathVariable Integer idNote) {
        service.delete(idNote);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    // Conversiones DTO <-> Entity
    private AppointmentNoteRequestDTO convertToDTO(AppointmentNote note) {
        return modelMapper.map(note, AppointmentNoteRequestDTO.class);
    }

    private AppointmentNote convertToEntity(AppointmentNoteRequestDTO noteDTO) {
        return modelMapper.map(noteDTO, AppointmentNote.class);
    }
}
