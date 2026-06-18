package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BoiteClientRequest {

    @NotBlank(message = "Le type est obligatoire")
    @Pattern(regexp = "16FO|24FO", message = "Le type doit être 16FO ou 24FO")
    private String type;

    @NotBlank(message = "Le splitter est obligatoire")
    private String splitterId;

    @NotNull private Double latitude;
    @NotNull private Double longitude;

    private String statut;
}