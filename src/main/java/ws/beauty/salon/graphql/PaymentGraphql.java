package ws.beauty.salon.graphql;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import graphql.schema.GraphQLScalarType;
import jakarta.validation.Valid;
import ws.beauty.salon.dto.PaymentRequest;
import ws.beauty.salon.dto.PaymentResponse;
import ws.beauty.salon.service.PaymentService;

@Controller
public class PaymentGraphql {
    @Autowired
    private PaymentService service;



    // 🔹 Obtener todos los pagos con paginación
    @QueryMapping
    public List<PaymentResponse> findAllPaymentsPaged(
            @Argument int page,
            @Argument int pageSize) {
        if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }
        return service.findAll(page, pageSize);
    }

    // 🔹 Obtener un pago por su ID
    @QueryMapping
    public PaymentResponse findPaymentById(@Argument Integer idPayment) {
        return service.findById(idPayment);
    }

    // 🔹 Buscar pago por ID de cita
    @QueryMapping
    public PaymentResponse findPaymentByAppointmentId(@Argument Integer appointmentId) {
        return service.findByAppointmentId(appointmentId);
    }

    // 🔹 Buscar pagos por cliente con paginación
    @QueryMapping
    public List<PaymentResponse> findPaymentsByClientId(
            @Argument Integer clientId,
            @Argument int page,
            @Argument int pageSize) {
        if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }
        return service.findByClientId(clientId, page, pageSize);
    }

    // 🔹 Buscar pagos por estilista con paginación
    @QueryMapping
    public List<PaymentResponse> findPaymentsByStylistId(
            @Argument Integer stylistId,
            @Argument int page,
            @Argument int pageSize) {
        if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }
        return service.findByStylistId(stylistId, page, pageSize);
    }

    // 🔹 Buscar pagos en un rango de fechas con paginación
    @QueryMapping
    public List<PaymentResponse> findPaymentsByDateRange(
            @Argument LocalDateTime start,
            @Argument LocalDateTime end,
            @Argument int page,
            @Argument int pageSize) {
        if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }
        return service.findByPaymentDateBetween(start, end, page, pageSize);
    }

    // 🔹 Calcular total de pagos por rango de fechas
    @QueryMapping
    public Double getTotalPaymentsBetweenDates(
            @Argument LocalDateTime start,
            @Argument LocalDateTime end) {
        return service.getTotalAmountBetweenDates(start, end);
    }

    // 🔹 Calcular total por cliente
    @QueryMapping
    public Double getTotalPaymentsByClient(@Argument Integer clientId) {
        return service.getTotalAmountByClient(clientId);
    }

    // 🔹 Calcular total por estilista
    @QueryMapping
    public Double getTotalPaymentsByStylist(@Argument Integer stylistId) {
        return service.getTotalAmountByStylist(stylistId);
    }

    // 🔹 Verificar si existe un pago para una cita
    @QueryMapping
    public Boolean existsPaymentByAppointment(@Argument Integer appointmentId) {
        return service.existsByAppointmentId(appointmentId);
    }

    // 🔹 Crear un nuevo pago
    @MutationMapping
    public PaymentResponse createPayment(@Valid @Argument PaymentRequest request) {
        return service.create(request);
    }

}
