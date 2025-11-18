package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ws.beauty.salon.dto.PaymentRequest;
import ws.beauty.salon.dto.PaymentResponse;


@Service
@Transactional
public interface  PaymentService {
    
    // 🔹 Obtener lista paginada de pagos
    List<PaymentResponse> findAll(int page, int pageSize);

    // 🔹 Obtener un pago por su ID
    PaymentResponse findById(Integer idPayment);

    // 🔹 Crear un nuevo pago
    PaymentResponse create(PaymentRequest request);


    // 🔹 Buscar pago por ID de cita
    PaymentResponse findByAppointmentId(Integer appointmentId);

    // 🔹 Buscar pagos por cliente con paginación
    List<PaymentResponse> findByClientId(Integer clientId, int page, int pageSize);

    // 🔹 Buscar pagos por estilista con paginación
    List<PaymentResponse> findByStylistId(Integer stylistId, int page, int pageSize);

    // 🔹 Buscar pagos en un rango de fechas con paginación
    List<PaymentResponse> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end, int page, int pageSize);

    // 🔹 Calcular total de pagos en un rango de fechas
    Double getTotalAmountBetweenDates(LocalDateTime start, LocalDateTime end);

    // 🔹 Calcular total de pagos por cliente
    Double getTotalAmountByClient(Integer clientId);

    // 🔹 Calcular total de pagos por estilista
    Double getTotalAmountByStylist(Integer stylistId);

    // 🔹 Verificar si existe un pago para una cita
    boolean existsByAppointmentId(Integer appointmentId);
}