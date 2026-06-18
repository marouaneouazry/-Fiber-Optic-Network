package ma.telecom.fibre.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.telecom.fibre.models.CheminFibre;

import java.util.List;

@Data
public class CheminFibreRequest {

    @NotNull(message = "La source est obligatoire")
    private CheminFibre.NoeudRef source;

    @NotNull(message = "La destination est obligatoire")
    private CheminFibre.NoeudRef destination;

    private String longueur;
    private String typeFibre;
    private String statut;
    private List<CheminFibre.Coordonnee> coordonnees;
}