package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StatutRequest {

    @NotBlank
    @Pattern(regexp = "actif|coupe|maintenance",
            message = "Le statut doit être : actif, coupe ou maintenance")
    private String statut;
}