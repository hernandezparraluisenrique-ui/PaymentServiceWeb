package ws.beauty.salon.service;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;

import jakarta.persistence.EntityNotFoundException;
import ws.beauty.salon.dto.NotificationRequest;
import ws.beauty.salon.dto.NotificationResponse;
import ws.beauty.salon.mapper.NotificationMapper;
import ws.beauty.salon.repository.ClientRepository;
import ws.beauty.salon.repository.NotificationRepository;
import ws.beauty.salon.model.Client;
import ws.beauty.salon.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

     private final NotificationRepository repository;
    private final ClientRepository clientRepository;


// Obtiene todas las notificaciones usando paginación (page y pageSize).
@Override
public List<NotificationResponse> findAll(int page, int pageSize) {
    PageRequest pageReq = PageRequest.of(page, pageSize);
    Page<Notification> notifications = repository.findAll(pageReq);
    return notifications.getContent().stream()
            .map(NotificationMapper::toResponse)
            .toList();
}

// Busca una notificación por su ID. Lanza error si no existe.
@Override
public NotificationResponse findById(Integer idNotification) {
    Notification notification = repository.findById(idNotification)
            .orElseThrow(() -> new EntityNotFoundException("Notification not found: " + idNotification));
    return NotificationMapper.toResponse(notification);
}

// Crea una nueva notificación validando primero que el cliente exista.
@Override
public NotificationResponse create(NotificationRequest request) {
    Client client = clientRepository.findById(request.getClientId())
            .orElseThrow(() -> new EntityNotFoundException("Client not found: " + request.getClientId()));

    Notification entity = NotificationMapper.toEntity(request, client);
    Notification saved = repository.save(entity);
    return NotificationMapper.toResponse(saved);
}

// Actualiza una notificación existente validando su existencia y la del cliente relacionado.
@Override
public NotificationResponse update(Integer idNotification, NotificationRequest request) {
    Notification existing = repository.findById(idNotification)
            .orElseThrow(() -> new EntityNotFoundException("Notification not found: " + idNotification));

    Client client = clientRepository.findById(request.getClientId())
            .orElseThrow(() -> new EntityNotFoundException("Client not found: " + request.getClientId()));

    NotificationMapper.copyToEntity(request, existing, client);
    Notification saved = repository.save(existing);
    return NotificationMapper.toResponse(saved);
}

// Obtiene todas las notificaciones enviadas a un cliente específico.
@Override
public List<NotificationResponse> findByClientId(Integer clientId) {
    return repository.findByClientId(clientId).stream()
            .map(NotificationMapper::toResponse)
            .toList();
}

// Obtiene las notificaciones enviadas por un canal específico (WhatsApp, Email, etc.)
@Override
public List<NotificationResponse> findBySentVia(String sentVia) {
    return repository.findBySentVia(sentVia).stream()
            .map(NotificationMapper::toResponse)
            .toList();
}

// Obtiene las notificaciones enviadas entre dos fechas.
@Override
public List<NotificationResponse> findBySentAtBetween(LocalDateTime start, LocalDateTime end) {
    return repository.findBySentAtBetween(start, end).stream()
            .map(NotificationMapper::toResponse)
            .toList();
}

// Cuenta cuántas notificaciones se han enviado a un cliente.
@Override
public long countByClientId(Integer clientId) {
    return repository.countByClientId(clientId);
}

}
