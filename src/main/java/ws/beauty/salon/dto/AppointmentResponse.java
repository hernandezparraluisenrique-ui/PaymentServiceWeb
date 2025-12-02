package ws.beauty.salon.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentResponse {
    private Integer idAppointment;   // o "id" según lo devuelva core
    private LocalDateTime appointmentDatetime;
    private String status;
    private Integer idClient;
}
