package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientResponse {
    Long idClient;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDateTime registrationDate;
    String preferences;
    String satisfactionHistory;
}
