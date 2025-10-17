package ws.beauty.salon.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ws.beauty.salon.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
 // 🔹 Buscar pago por ID de cita
    Optional<Payment> findByAppointmentId(Integer appointmentId);

    // 🔹 Buscar pagos por cliente
    @Query("SELECT p FROM Payment p WHERE p.appointment.client.id = :clientId")
    List<Payment> findByClientId(@Param("clientId") Integer clientId);

    // 🔹 Buscar pagos por estilista
    @Query("SELECT p FROM Payment p WHERE p.appointment.stylist.id = :stylistId")
    List<Payment> findByStylistId(@Param("stylistId") Integer stylistId);

    // 🔹 Buscar pagos en un rango de fechas
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);

    // 🔹 Calcular total de pagos en un rango de fechas
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end")
    Double getTotalAmountBetweenDates(@Param("start") LocalDateTime start, 
                                      @Param("end") LocalDateTime end);

    // 🔹 Calcular total de pagos por cliente
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.appointment.client.id = :clientId")
    Double getTotalAmountByClient(@Param("clientId") Integer clientId);

    // 🔹 Calcular total de pagos por estilista
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.appointment.stylist.id = :stylistId")
    Double getTotalAmountByStylist(@Param("stylistId") Integer stylistId);

    // 🔹 Verificar si existe un pago para una cita
    boolean existsByAppointmentId(Integer appointmentId);

}
