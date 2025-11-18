package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import ws.beauty.salon.dto.NotificationRequest;
import ws.beauty.salon.dto.NotificationResponse;



public interface NotificationService {
    

    // 🔹 Obtener todas las notificaciones con paginación
    List<NotificationResponse> findAll(int page, int pageSize);

    // 🔹 Buscar por ID
    NotificationResponse findById(Integer idNotification);

    // 🔹 Crear nueva notificación
    NotificationResponse create(NotificationRequest request);

    // 🔹 Actualizar notificación existente
    NotificationResponse update(Integer idNotification, NotificationRequest request);


    // 🔹 Buscar por cliente
    List<NotificationResponse> findByClientId(Integer clientId);

    // 🔹 Buscar por tipo de envío
    List<NotificationResponse> findBySentVia(String sentVia);

    // 🔹 Buscar en rango de fechas
    List<NotificationResponse> findBySentAtBetween(LocalDateTime start, LocalDateTime end);

    // 🔹 Contar notificaciones por cliente
    long countByClientId(Integer clientId);

  
}
