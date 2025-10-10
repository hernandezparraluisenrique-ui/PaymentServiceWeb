package ws.beauty.salon.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {
    private Integer id;
    private Integer clientId;
    private String message;
    private String sentVia;
    private LocalDateTime sentAt;
}

