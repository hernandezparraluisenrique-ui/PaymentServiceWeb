package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ws.beauty.salon.model.AttendanceLog;
import ws.beauty.salon.repository.AttendanceLogRepository;

@Service
@Transactional
public class AttendanceLogService {

    @Autowired
    private AttendanceLogRepository repository;

    public List<AttendanceLog> getAll() {
        return repository.findAll();
    }

    public AttendanceLog save(AttendanceLog log) {
        return repository.save(log);
    }

    public List<AttendanceLog> getByStylistId(Integer stylistId) {
        return repository.findByStylistId(stylistId);
    }

    public List<AttendanceLog> getByStylistIdAndDateRange(Integer stylistId, LocalDateTime start, LocalDateTime end) {
        return repository.findByStylistIdAndDateRange(stylistId, start, end);
    }

    public List<AttendanceLog> getActiveAttendances() {
        return repository.findActiveAttendances();
    }

    public AttendanceLog getLastAttendanceByStylist(Integer stylistId) {
        List<AttendanceLog> list = repository.findLastAttendanceByStylist(stylistId);
        return list.isEmpty() ? null : list.get(0);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
