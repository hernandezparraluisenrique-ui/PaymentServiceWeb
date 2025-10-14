package ws.beauty.salon.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ws.beauty.salon.dto.AttendanceLogRequestDTO;
import ws.beauty.salon.model.AttendanceLog;
import ws.beauty.salon.model.Stylist;
import ws.beauty.salon.service.AttendanceLogService;
import ws.beauty.salon.service.StylistService; // servicio para obtener Stylist

@RestController
@RequestMapping("/attendanceLogs")
@CrossOrigin(origins = "*")
public class AttendanceLogController {

    @Autowired
    private AttendanceLogService service;

    @Autowired
    private StylistService stylistService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<AttendanceLog> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceLog> getById(@PathVariable Integer id) {
        return service.getAll().stream()
                .filter(log -> log.getId().equals(id))
                .findFirst()
                .map(log -> new ResponseEntity<>(log, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AttendanceLogRequestDTO> add(@RequestBody AttendanceLogRequestDTO dto) {
        AttendanceLog log = convertToEntity(dto);

        // Buscar el estilista por ID
        Optional<Stylist> optionalStylist = stylistService.findById(dto.getStylistId());//findById(dto.getStylistId());
        if (optionalStylist.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // o NOT_FOUND si lo prefieres
        }

        log.setStylist(optionalStylist.get());

        AttendanceLog saved = service.save(log);
        return new ResponseEntity<>(convertToDTO(saved), HttpStatus.CREATED);
    }

    private AttendanceLog convertToEntity(AttendanceLogRequestDTO dto) {
    return modelMapper.map(dto, AttendanceLog.class);
}

private AttendanceLogRequestDTO convertToDTO(AttendanceLog entity) {
    return modelMapper.map(entity, AttendanceLogRequestDTO.class);
}


}
