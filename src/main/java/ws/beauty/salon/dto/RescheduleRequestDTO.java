package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RescheduleRequestDTO {
    private Long idRequest;
    private Long appointmentId;
    private Long idClient;
    private LocalDateTime requestedDate;
    private String status;
    private String reason;
    private LocalDateTime createdAt;

}
