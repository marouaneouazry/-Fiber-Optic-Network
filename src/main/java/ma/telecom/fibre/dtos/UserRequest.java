package ma.telecom.fibre.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private boolean actif = true;
}
