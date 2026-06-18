package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipementRequest {

    @NotBlank(message = "Le type est obligatoire")
    private String type; // OLT | Switch | Routeur

    private String ip;
    private String marque;
    private String modele;
    private String emplacement;
    private String repartiteurId;
    private String statut;
}