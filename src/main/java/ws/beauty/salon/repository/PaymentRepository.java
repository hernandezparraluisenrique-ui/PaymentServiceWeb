package ws.beauty.salon.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ws.beauty.salon.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    // 🔹 Buscar pago por ID de cita (sin paginación - retorna un solo resultado)
    Optional<Payment> findByAppointmentId(Integer appointmentId);
    
    // 🔹 Buscar pagos por cliente (con paginación)
    @Query("SELECT p FROM Payment p WHERE p.appointment.client.id = :clientId")
    Page<Payment> findByClientId(@Param("clientId") Integer clientId, Pageable pageable);
    
    // 🔹 Buscar pagos por estilista (con paginación)
    @Query("SELECT p FROM Payment p WHERE p.appointment.stylist.id = :stylistId")
    Page<Payment> findByStylistId(@Param("stylistId") Integer stylistId, Pageable pageable);
    
    // 🔹 Buscar pagos en un rango de fechas (con paginación)
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end")
    Page<Payment> findByPaymentDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);
    
    // 🔹 Calcular total de pagos en un rango de fechas
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end")
    Double getTotalAmountBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    // 🔹 Calcular total de pagos por cliente
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.appointment.client.id = :clientId")
    Double getTotalAmountByClient(@Param("clientId") Integer clientId);
    
    // 🔹 Calcular total de pagos por estilista
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.appointment.stylist.id = :stylistId")
    Double getTotalAmountByStylist(@Param("stylistId") Integer stylistId);
    
    // 🔹 Verificar si existe un pago para una cita
    boolean existsByAppointmentId(Integer appointmentId);
}

