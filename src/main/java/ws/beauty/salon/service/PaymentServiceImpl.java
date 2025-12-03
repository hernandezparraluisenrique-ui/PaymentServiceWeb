package ws.beauty.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ws.beauty.salon.client.AppointmentGatewayClient;
import ws.beauty.salon.dto.PaymentRequest;
import ws.beauty.salon.dto.PaymentResponse;
import ws.beauty.salon.mapper.PaymentMapper;
import ws.beauty.salon.model.Payment;
import ws.beauty.salon.repository.PaymentRepository;



@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


      private final PaymentRepository paymentRepository;
    private final AppointmentGatewayClient apiGatewayClient; // FEIGN CLIENT que pasa por el Gateway

    // -------------------------------------------------------------
    // 🔹 Crear un nuevo pago (VALIDA Appointment DESDE API GATEWAY)
    // -------------------------------------------------------------
    @Override
    public PaymentResponse create(PaymentRequest request) {

        var appointment = apiGatewayClient.getAppointmentById(request.getAppointmentId());

        if (appointment == null) {
            throw new EntityNotFoundException("Appointment not found: " + request.getAppointmentId());
        }

        Payment payment = PaymentMapper.toEntity(request);

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

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
    // 🔹 Buscar pago por ID de cita
    // -------------------------------------------------------------
    @Override
    public PaymentResponse findByAppointmentId(Integer appointmentId) {
        Payment payment = paymentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment not found for appointment ID: " + appointmentId));
        return PaymentMapper.toResponse(payment);
    }

    // -------------------------------------------------------------
    // 🔹 Buscar pagos por cliente (filtrando por appointments desde Gateway)
    // -------------------------------------------------------------
    @Override
    public List<PaymentResponse> findByClientId(Integer clientId, int page, int pageSize) {
        // 1️⃣ Traer todas las appointments del cliente
        List<Integer> appointmentIds = apiGatewayClient.getAppointmentsByClientId(clientId)
                .stream().map(a -> a.getIdAppointment()).toList();

        if (appointmentIds.isEmpty()) return List.of();

        // 2️⃣ Filtrar los payments locales
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findAllByAppointmentIdIn(appointmentIds, pageReq);

        return payments.getContent().stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    // -------------------------------------------------------------
    // 🔹 Buscar pagos por estilista (filtrando por appointments desde Gateway)
    // -------------------------------------------------------------
    @Override
    public List<PaymentResponse> findByStylistId(Integer stylistId, int page, int pageSize) {
        List<Integer> appointmentIds = apiGatewayClient.getAppointmentsByStylistId(stylistId)
                .stream().map(a -> a.getIdAppointment()).toList();

        if (appointmentIds.isEmpty()) return List.of();

        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Payment> payments = paymentRepository.findAllByAppointmentIdIn(appointmentIds, pageReq);

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
    // 🔹 Total por cliente (filtrando por appointments)
    // -------------------------------------------------------------
    @Override
    public Double getTotalAmountByClient(Integer clientId) {
        List<Integer> appointmentIds = apiGatewayClient.getAppointmentsByClientId(clientId)
                .stream().map(a -> a.getIdAppointment()).toList();

        if (appointmentIds.isEmpty()) return 0.0;

        return paymentRepository.getTotalAmountByAppointmentIds(appointmentIds);
    }

    // -------------------------------------------------------------
    // 🔹 Total por estilista (filtrando por appointments)
    // -------------------------------------------------------------
    @Override
    public Double getTotalAmountByStylist(Integer stylistId) {
        List<Integer> appointmentIds = apiGatewayClient.getAppointmentsByStylistId(stylistId)
                .stream().map(a -> a.getIdAppointment()).toList();

        if (appointmentIds.isEmpty()) return 0.0;

        return paymentRepository.getTotalAmountByAppointmentIds(appointmentIds);
    }

    // -------------------------------------------------------------
    // 🔹 Validar si existe un pago por cita
    // -------------------------------------------------------------
    @Override
    public boolean existsByAppointmentId(Integer appointmentId) {
        return paymentRepository.existsByAppointmentId(appointmentId);
    }
}