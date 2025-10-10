package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResponse {
    @JsonProperty("id payment")
    private Integer id;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("payment date")
    private LocalDateTime paymentDate;

    @JsonProperty("id appointment")
    private Integer appointmentId;
}
