package ws.beauty.salon.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ws.beauty.salon.dto.PaymentRequest;
import ws.beauty.salon.dto.PaymentResponse;
import ws.beauty.salon.model.Payment;

public class PaymentMapper {
     // Convierte entidad a respuesta (Payment → PaymentResponse)
    public static PaymentResponse toResponse(Payment payment) {
        if (payment == null)
            return null;

        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount().doubleValue())
                .paymentDate(payment.getPaymentDate())
                .appointmentId(payment.getAppointmentId())  // ← correcto
                .build();
    }

    // Convierte request a entidad (PaymentRequest → Payment)
    public static Payment toEntity(PaymentRequest dto) {
        if (dto == null)
            return null;

        return Payment.builder()
                .amount(BigDecimal.valueOf(dto.getAmount()))
                .paymentDate(
                        dto.getPaymentDate() != null
                                ? dto.getPaymentDate()
                                : LocalDateTime.now()
                )
                .appointmentId(dto.getAppointmentId())  // ← correcto
                .build();
    }

    // Actualiza entidad existente (update)
    public static void copyToEntity(PaymentRequest dto, Payment entity) {
        if (dto == null || entity == null)
            return;

        entity.setAmount(BigDecimal.valueOf(dto.getAmount()));
        entity.setPaymentDate(
                dto.getPaymentDate() != null
                        ? dto.getPaymentDate()
                        : LocalDateTime.now()
        );
        entity.setAppointmentId(dto.getAppointmentId());  // ← correcto
    }
}
