package ws.beauty.salon.dto;

import java.time.LocalDateTime;

public class AttendanceLogRequestDTO {
    private Integer id;
    private Integer stylistId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getStylistId() { return stylistId; }
    public void setStylistId(Integer stylistId) { this.stylistId = stylistId; }

    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }

    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }
}

