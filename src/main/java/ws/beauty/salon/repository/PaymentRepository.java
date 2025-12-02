package ws.beauty.salon.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import ws.beauty.salon.model.Payment;
import org.springframework.data.repository.query.Param;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

   /**
     * Busca un pago asociado a una cita específica.
     */
    Optional<Payment> findByAppointmentId(Integer appointmentId);

    /**
     * Busca todos los pagos relacionados con un conjunto de citas.
     * Esto sirve para cliente o estilista, usando appointmentIds.
     */
    Page<Payment> findAllByAppointmentIdIn(List<Integer> appointmentIds, Pageable pageable);

    /**
     * Calcula el total de pagos de un conjunto de citas.
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.appointmentId IN :appointmentIds")
    Double getTotalAmountByAppointmentIds(@Param("appointmentIds") List<Integer> appointmentIds);

    /**
     * Busca pagos en un rango de fechas.
     */
    Page<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * Calcula total de pagos en un rango de fechas.
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end")
    Double getTotalAmountBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    /**
     * Verifica si existe un pago para una cita.
     */
    boolean existsByAppointmentId(Integer appointmentId);
    
}



