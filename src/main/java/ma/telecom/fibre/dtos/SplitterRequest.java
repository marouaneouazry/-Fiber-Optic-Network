package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SplitterRequest {

    @Pattern(regexp = "1:(8|16|32)", message = "Le ratio doit être 1:8, 1:16 ou 1:32")
    private String ratio;

    @NotBlank(message = "Le répartiteur est obligatoire")
    private String repartiteurId;

    @NotNull private Double latitude;
    @NotNull private Double longitude;

    private String statut;
}