package ws.beauty.salon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    
    @NotBlank(message = "Username is required")
    @Size(max = 50)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String password;
    
    @NotBlank(message = "Role is required")
    @Size(max = 20)
    private String role;
    
    private Integer clientId;
    private Integer stylistId;
}
