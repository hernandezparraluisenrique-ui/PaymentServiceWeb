package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ws.beauty.salon.dto.AttendanceLogRequest;
import ws.beauty.salon.dto.AttendanceLogResponse;
import ws.beauty.salon.mapper.AttendanceLogMapper;
import ws.beauty.salon.model.AttendanceLog;
import ws.beauty.salon.model.Stylist;
import ws.beauty.salon.repository.AttendanceLogRepository;
import ws.beauty.salon.repository.StylistRepository;


@Service
@RequiredArgsConstructor
public class AttendanceLogServiceImpl implements AttendanceLogService{
   private final AttendanceLogRepository attendanceRepository;
    private final StylistRepository stylistRepository;


// Obtiene los registros de asistencia de forma paginada.
@Override
public List<AttendanceLogResponse> findAll(int page, int pageSize) {
    PageRequest pageReq = PageRequest.of(page, pageSize);
    Page<AttendanceLog> pageLogs = attendanceRepository.findAll(pageReq);
    return pageLogs.getContent().stream()
            .map(AttendanceLogMapper::toResponse)
            .toList();
}

// Busca un registro de asistencia por su ID.
@Override
public AttendanceLogResponse findById(Integer idAttendance) {
    AttendanceLog log = attendanceRepository.findById(idAttendance)
            .orElseThrow(() -> new EntityNotFoundException("Attendance log not found: " + idAttendance));
    return AttendanceLogMapper.toResponse(log);
}

// Crea un nuevo registro de asistencia (check-in) para un estilista.
@Override
public AttendanceLogResponse create(AttendanceLogRequest request) {
    Stylist stylist = stylistRepository.findById(request.getStylistId())
            .orElseThrow(() -> new EntityNotFoundException("Stylist not found: " + request.getStylistId()));

    AttendanceLog log = AttendanceLogMapper.toEntity(request, stylist);
    AttendanceLog saved = attendanceRepository.save(log);
    return AttendanceLogMapper.toResponse(saved);
}

// Actualiza un registro de asistencia existente.
@Override
public AttendanceLogResponse update(Integer idAttendance, AttendanceLogRequest request) {
    AttendanceLog existing = attendanceRepository.findById(idAttendance)
            .orElseThrow(() -> new EntityNotFoundException("Attendance log not found: " + idAttendance));

    Stylist stylist = stylistRepository.findById(request.getStylistId())
            .orElseThrow(() -> new EntityNotFoundException("Stylist not found: " + request.getStylistId()));

    AttendanceLogMapper.copyToEntity(request, existing, stylist);
    AttendanceLog saved = attendanceRepository.save(existing);
    return AttendanceLogMapper.toResponse(saved);
}

// Obtiene todos los registros de asistencia de un estilista.
@Override
public List<AttendanceLogResponse> findByStylistId(Integer stylistId) {
    return attendanceRepository.findByStylistId(stylistId).stream()
            .map(AttendanceLogMapper::toResponse)
            .toList();
}

// Busca registros cuyo check-in esté dentro de un rango de fechas.
@Override
public List<AttendanceLogResponse> findByCheckInBetween(LocalDateTime start, LocalDateTime end) {
    return attendanceRepository.findByCheckInBetween(start, end).stream()
            .map(AttendanceLogMapper::toResponse)
            .toList();
}

// Verifica si el estilista tiene un registro de asistencia abierto (sin check-out).
@Override
public boolean hasOpenAttendance(Integer stylistId) {
    return attendanceRepository.findByStylistIdAndCheckOutIsNull(stylistId).isPresent();
}

// Cierra la asistencia abierta del estilista (registra el check-out).
@Override
public AttendanceLogResponse closeAttendance(Integer stylistId) {
    AttendanceLog log = attendanceRepository.findByStylistIdAndCheckOutIsNull(stylistId)
            .orElseThrow(() -> new EntityNotFoundException("No open attendance found for stylist: " + stylistId));

    log.setCheckOut(LocalDateTime.now());
    AttendanceLog saved = attendanceRepository.save(log);
    return AttendanceLogMapper.toResponse(saved);
}

// Cuenta cuántos registros de asistencia tiene un estilista.
@Override
public long countByStylistId(Integer stylistId) {
    return attendanceRepository.countByStylistId(stylistId);
}

// Cuenta los registros cuyo check-in está dentro de un rango de fechas.
@Override
public long countByCheckInBetween(LocalDateTime start, LocalDateTime end) {
    return attendanceRepository.countByCheckInBetween(start, end);
}   
}