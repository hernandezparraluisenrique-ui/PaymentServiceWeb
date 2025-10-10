package ws.beauty.salon.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ws.beauty.salon.dto.NotificationRequestDTO;
import ws.beauty.salon.dto.NotificationResponseDTO;
import ws.beauty.salon.model.Notification;
import ws.beauty.salon.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Notification> getAll() {
        return service.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getById(@PathVariable Integer id) {
        Notification notification = service.getNotificationById(id);
        return ResponseEntity.ok(modelMapper.map(notification, NotificationResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> add(@RequestBody NotificationRequestDTO dto) {
        Notification saved = service.createNotification(dto);
        return new ResponseEntity<>(modelMapper.map(saved, NotificationResponseDTO.class), HttpStatus.CREATED);
    }
}
