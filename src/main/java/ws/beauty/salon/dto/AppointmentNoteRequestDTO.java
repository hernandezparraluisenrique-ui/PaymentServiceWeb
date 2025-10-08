package ws.beauty.salon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AppointmentNoteRequestDTO {
    @JsonProperty("id note")
    private Integer id;

    @JsonProperty("id appointment ")
    private Integer appointmentId;

    @JsonProperty("note text")
    private String noteText;
}
