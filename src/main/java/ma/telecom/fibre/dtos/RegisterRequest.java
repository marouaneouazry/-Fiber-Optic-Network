package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ma.telecom.fibre.models.Role;

@Data
public class RegisterRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    @Email   private String email;
    private Role role;
}