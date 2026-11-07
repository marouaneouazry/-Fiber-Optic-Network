package ma.telecom.fibre.dtos;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String role;
    private boolean actif;
    private LocalDateTime createdAt;
}
