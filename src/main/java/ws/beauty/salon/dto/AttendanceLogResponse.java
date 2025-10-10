package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttendanceLogResponse {
    @JsonProperty("id attendance")
    private Integer id;

    @JsonProperty("id stylist")
    private Integer stylistId;

    @JsonProperty("stylist name")
    private String stylistName;

    @JsonProperty("check in")
    private LocalDateTime checkIn;

    @JsonProperty("check out")
    private LocalDateTime checkOut;
}
