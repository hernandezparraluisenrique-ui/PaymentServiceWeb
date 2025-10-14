package ws.beauty.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentNoteResponse {
    private Integer idNote;
    private Integer idAppointment;
    private String noteText;
}

