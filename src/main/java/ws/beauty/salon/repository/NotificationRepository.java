package ws.beauty.salon.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ws.beauty.salon.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByClientId(Integer clientId);

    List<Notification> findBySentVia(String sentVia);

    List<Notification> findByClientIdAndSentVia(Integer clientId, String sentVia);

    List<Notification> findBySentAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT n FROM Notification n WHERE n.clientId = :clientId AND n.sentAt BETWEEN :start AND :end")
    List<Notification> findByClientIdAndDateRange(@Param("clientId") Integer clientId,
                                                  @Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end);

    long countByClientId(Integer clientId);

    long countBySentVia(String sentVia);

    List<Notification> findTop10ByClientIdOrderBySentAtDesc(Integer clientId);

    @Query("SELECT n FROM Notification n WHERE n.sentAt >= :since ORDER BY n.sentAt DESC")
    List<Notification> findRecentNotifications(@Param("since") LocalDateTime since);
}
