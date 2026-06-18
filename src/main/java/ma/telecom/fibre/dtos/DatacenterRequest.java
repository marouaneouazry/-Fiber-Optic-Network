package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DatacenterRequest {
    @NotBlank(message = "Le nom est obligatoire") private String nom;
    @NotBlank private String localisation;
    private int capacite;
    @NotNull private Double latitude;
    @NotNull private Double longitude;
    private String statut;
}