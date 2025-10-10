package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ClientRequestDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private String preferences;
    private String satisfactionHistory;
}
