package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AppointmentResponse {
    Long idAppointment;
    LocalDateTime appointmentDatetime;
    String status;
    Long idClient;
    Long idStylist;
    Long idService;
}
