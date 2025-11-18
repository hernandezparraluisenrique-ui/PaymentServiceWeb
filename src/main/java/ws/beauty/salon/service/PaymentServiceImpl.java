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
import ws.beauty.salon.model.Appointment;
import ws.beauty.salon.model.Payment;
import ws.beauty.salon.repository.AppointmentRepository;
import ws.beauty.salon.repository.PaymentRepository;



@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    // 🔹 Obtener todos los pagos con paginación
    @Override
    public List<PaymentResponse> findAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findAll(pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // 🔹 Obtener un pago por su ID
    @Override
    public PaymentResponse findById(Integer idPayment) {
        Payment payment = paymentRepository.findById(idPayment)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found: " + idPayment));
        return PaymentMapper.toResponse(payment);
    }

    // 🔹 Crear un nuevo pago
    @Override
    public PaymentResponse create(PaymentRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found: " + request.getAppointmentId()));

        Payment payment = PaymentMapper.toEntity(request, appointment);
        Payment saved = paymentRepository.save(payment);
        return PaymentMapper.toResponse(saved);
    }

   
    // 🔹 Buscar pago por ID de cita
    @Override
    public PaymentResponse findByAppointmentId(Integer appointmentId) {
        Payment payment = paymentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found for appointment ID: " + appointmentId));
        return PaymentMapper.toResponse(payment);
    }

    // 🔹 Buscar pagos por cliente con paginación
    @Override
    public List<PaymentResponse> findByClientId(Integer clientId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findByClientId(clientId, pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // 🔹 Buscar pagos por estilista con paginación
    @Override
    public List<PaymentResponse> findByStylistId(Integer stylistId, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findByStylistId(stylistId, pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // 🔹 Buscar pagos en un rango de fechas con paginación
    @Override
    public List<PaymentResponse> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end, int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findByPaymentDateBetween(start, end, pageReq);
        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // 🔹 Calcular total de pagos en un rango de fechas
    @Override
    public Double getTotalAmountBetweenDates(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.getTotalAmountBetweenDates(start, end);
    }

    // 🔹 Calcular total de pagos por cliente
    @Override
    public Double getTotalAmountByClient(Integer clientId) {
        return paymentRepository.getTotalAmountByClient(clientId);
    }

    // 🔹 Calcular total de pagos por estilista
    @Override
    public Double getTotalAmountByStylist(Integer stylistId) {
        return paymentRepository.getTotalAmountByStylist(stylistId);
    }

    // 🔹 Verificar si existe un pago para una cita
    @Override
    public boolean existsByAppointmentId(Integer appointmentId) {
        return paymentRepository.existsByAppointmentId(appointmentId);
    }
}