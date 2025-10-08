package ws.beauty.salon.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private Integer idUser;
    private String userName;
    private String password;
    private String role;
    private Integer clientId;
    private Integer stylistId;
}
