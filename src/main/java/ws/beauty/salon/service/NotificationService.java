package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ws.beauty.salon.dto.NotificationRequestDTO;
import ws.beauty.salon.model.Notification;
import ws.beauty.salon.repository.NotificationRepository;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    public Notification getNotificationById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    public Notification createNotification(NotificationRequestDTO dto) {
        Notification notification = modelMapper.map(dto, Notification.class);
        if (notification.getSentAt() == null) {
            notification.setSentAt(LocalDateTime.now());
        }
        return repository.save(notification);
    }

    // Otros métodos según necesidad (buscar por cliente, rango de fechas, contadores, recientes)
}
