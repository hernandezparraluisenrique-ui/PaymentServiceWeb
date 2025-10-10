package ws.beauty.salon.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ws.beauty.salon.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
 // Buscar pago por ID de cita
    Optional<Payment> findByAppointmentId(Integer appointmentId);
    
    // Buscar pagos por cliente
    @Query("SELECT p FROM Payment p WHERE p.appointment.client.id = :clientId")
    List<Payment> findByClientId(@Param("clientId") Integer clientId);
    
    // Buscar pagos por estilista
    @Query("SELECT p FROM Payment p WHERE p.appointment.stylist.id = :stylistId")
    List<Payment> findByStylistId(@Param("stylistId") Integer stylistId);
    
    // Buscar pagos en un rango de fechas
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
    
    // Calcular total de pagos en un rango de fechas
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end")
    Double getTotalAmountBetweenDates(@Param("start") LocalDateTime start, 
                                      @Param("end") LocalDateTime end);
    
    // Calcular total de pagos por cliente
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.appointment.client.id = :clientId")
    Double getTotalAmountByClient(@Param("clientId") Integer clientId);
    
    // Calcular total de pagos por estilista
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.appointment.stylist.id = :stylistId")
    Double getTotalAmountByStylist(@Param("stylistId") Integer stylistId);
    
    // Verificar si existe pago para una cita
    boolean existsByAppointmentId(Integer appointmentId);

}
