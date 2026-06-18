package ma.telecom.fibre.models;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "equipements")
public class Equipement {

    @Id
    private String id;

    @NotBlank
    private String type; // OLT | Switch | Routeur

    private String ip;
    private String marque;
    private String modele;
    private String emplacement;

    @Indexed
    private String repartiteurId;

    @Builder.Default
    private String statut = "actif"; // actif | coupe | maintenance

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}