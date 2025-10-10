package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class PaymentRequestDTO {
    private Integer id;
    private Double amount;
    private LocalDateTime paymentDate;
    private Integer appointmentId;

}
