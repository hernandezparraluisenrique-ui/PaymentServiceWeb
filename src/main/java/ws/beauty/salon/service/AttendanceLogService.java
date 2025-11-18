package ws.beauty.salon.service;


import java.time.LocalDateTime;
import java.util.List;

import ws.beauty.salon.dto.AttendanceLogRequest;
import ws.beauty.salon.dto.AttendanceLogResponse;

public interface AttendanceLogService {
  
    // Busca un registro de asistencia por su ID.
    AttendanceLogResponse findById(Integer idAttendance);

    // Crea un nuevo registro de asistencia (check-in de un stylist).
    AttendanceLogResponse create(AttendanceLogRequest request);

    // Actualiza un registro de asistencia existente.
    AttendanceLogResponse update(Integer idAttendance, AttendanceLogRequest request);

    // Obtiene todos los registros de asistencia pertenecientes a un estilista.
    List<AttendanceLogResponse> findByStylistId(Integer stylistId);

    // Busca registros cuyo check-in esté dentro de un rango de fechas.
    List<AttendanceLogResponse> findByCheckInBetween(LocalDateTime start, LocalDateTime end);

    // Verifica si el estilista tiene un registro de asistencia abierto (sin check-out).
    boolean hasOpenAttendance(Integer stylistId);

    // Cierra la asistencia abierta del estilista (registra el check-out).
    AttendanceLogResponse closeAttendance(Integer stylistId);

    // Obtiene registros de asistencia de forma paginada.
    List<AttendanceLogResponse> findAll(int page, int pageSize);

    // Cuenta cuántos registros de asistencia tiene un estilista.
    long countByStylistId(Integer stylistId);

    // Cuenta registros cuyo check-in esté dentro de un rango específico.
    long countByCheckInBetween(LocalDateTime start, LocalDateTime end);
}
