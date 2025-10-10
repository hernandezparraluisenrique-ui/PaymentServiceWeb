package ws.beauty.salon.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ws.beauty.salon.model.AttendanceLog;

public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Integer> {

    List<AttendanceLog> findByStylistId(Integer stylistId);

    @Query("SELECT a FROM AttendanceLog a WHERE a.stylist.id = :stylistId " +
           "AND a.checkIn BETWEEN :startDate AND :endDate")
    List<AttendanceLog> findByStylistIdAndDateRange(
        @Param("stylistId") Integer stylistId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT a FROM AttendanceLog a WHERE a.checkOut IS NULL")
    List<AttendanceLog> findActiveAttendances();

    @Query("SELECT a FROM AttendanceLog a WHERE a.stylist.id = :stylistId " +
           "ORDER BY a.checkIn DESC")
    List<AttendanceLog> findLastAttendanceByStylist(@Param("stylistId") Integer stylistId);
}
