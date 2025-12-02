package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ws.beauty.salon.dto.PaymentRequest;
import ws.beauty.salon.dto.PaymentResponse;
import ws.beauty.salon.mapper.PaymentMapper;
import ws.beauty.salon.model.Payment;
import ws.beauty.salon.repository.PaymentRepository;
import client.AppointmentGatewayClient;



@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


     private final PaymentRepository paymentRepository;
    private final AppointmentGatewayClient apiGatewayClient; // ⬅️ FEIGN CLIENT QUE PASA POR EL GATEWAY

    // -------------------------------------------------------------
    // 🔹 Crear un nuevo pago (VALIDA Appointment DESDE API GATEWAY)
    // -------------------------------------------------------------
@Override
public PaymentResponse create(PaymentRequest request) {

    // 1️⃣ Validar que la cita existe via API Gateway
    var appointment = apiGatewayClient.getAppointmentById(request.getAppointmentId());

    if (appointment == null) {
        throw new EntityNotFoundException("Appointment not found: " + request.getAppointmentId());
    }

    // 2️⃣ Crear el pago (solo lo que esta en la tabla Payments)
    Payment payment = PaymentMapper.toEntity(request);

    // Asegurar la fecha si no viene
    if (payment.getPaymentDate() == null) {
        payment.setPaymentDate(LocalDateTime.now());
    }

    // 3️⃣ Guardar
    Payment saved = paymentRepository.save(payment);

    return PaymentMapper.toResponse(saved);
}

    // -------------------------------------------------------------
    // 🔹 Obtener todos los pagos con paginación
    // -------------------------------------------------------------
    @Override
    public List<PaymentResponse> findAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findAll(pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // -------------------------------------------------------------
    // 🔹 Obtener un pago por ID
    // -------------------------------------------------------------
    @Override
    public PaymentResponse findById(Integer idPayment) {
        Payment payment = paymentRepository.findById(idPayment)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found: " + idPayment));
        return PaymentMapper.toResponse(payment);
    }

    // -------------------------------------------------------------
    // 🔹 Buscar pago por ID de cita (NO necesita Gateway)
    // -------------------------------------------------------------
    @Override
    public PaymentResponse findByAppointmentId(Integer appointmentId) {
        Payment payment = paymentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment not found for appointment ID: " + appointmentId));
        return PaymentMapper.toResponse(payment);
    }

    // -------------------------------------------------------------
    // 🔹 Buscar pagos por cliente
    // -------------------------------------------------------------
    @Override
    public List<PaymentResponse> findByClientId(Integer clientId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findByClientId(clientId, pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // -------------------------------------------------------------
    // 🔹 Buscar pagos por estilista
    // -------------------------------------------------------------
    @Override
    public List<PaymentResponse> findByStylistId(Integer stylistId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findByStylistId(stylistId, pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // -------------------------------------------------------------
    // 🔹 Buscar por rango de fechas
    // -------------------------------------------------------------
    @Override
    public List<PaymentResponse> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findByPaymentDateBetween(start, end, pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // -------------------------------------------------------------
    // 🔹 Total por rango
    // -------------------------------------------------------------
    @Override
    public Double getTotalAmountBetweenDates(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.getTotalAmountBetweenDates(start, end);
    }

    // -------------------------------------------------------------
    // 🔹 Total por cliente
    // -------------------------------------------------------------
    @Override
    public Double getTotalAmountByClient(Integer clientId) {
        return paymentRepository.getTotalAmountByClient(clientId);
    }

    // -------------------------------------------------------------
    // 🔹 Total por estilista
    // -------------------------------------------------------------
    @Override
    public Double getTotalAmountByStylist(Integer stylistId) {
        return paymentRepository.getTotalAmountByStylist(stylistId);
    }

    // -------------------------------------------------------------
    // 🔹 Validar si existe un pago por cita
    // -------------------------------------------------------------
    @Override
    public boolean existsByAppointmentId(Integer appointmentId) {
        return paymentRepository.existsByAppointmentId(appointmentId);
    }
}