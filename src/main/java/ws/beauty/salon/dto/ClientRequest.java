package ws.beauty.salon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phone;

    // Opcionales — pueden venir en blanco o null
    private String preferences;

    private String satisfactionHistory;

    // Si quieres permitir establecer manualmente la fecha (no obligatorio)
    private LocalDateTime registrationDate;
}
