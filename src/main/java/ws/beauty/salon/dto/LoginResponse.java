package ws.beauty.salon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("expires_in")
    private long expiresIn;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("role")
    private String role;
}