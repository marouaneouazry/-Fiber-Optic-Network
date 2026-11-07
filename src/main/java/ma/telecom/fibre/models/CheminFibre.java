package ma.telecom.fibre.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "chemins")
public class CheminFibre {

    @Id
    private String id;

    // RG05 : source et destination obligatoires
    @NotNull private NoeudRef source;
    @NotNull private NoeudRef destination;

    private String longueur;   // ex: "2.5km"
    private String typeFibre;  // ex: "SMF G.652D"

    @Builder.Default
    private String statut = "actif";

    // Points GPS du tracé sur la carte
    private List<Coordonnee> coordonnees;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class NoeudRef {
        private String type; // datacenter | repartiteur | splitter | boite
        private String id;
        private String nom; 
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Coordonnee {
        private int numPoint;
        private Double lat;
        private Double lng;
    }
}