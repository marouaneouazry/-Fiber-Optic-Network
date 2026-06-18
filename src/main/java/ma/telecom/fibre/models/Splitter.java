package ma.telecom.fibre.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "splitters")
public class Splitter {

    @Id
    private String id;

    // RG02 : ratio doit être 1:8, 1:16 ou 1:32
    @Pattern(regexp = "1:(8|16|32)", message = "Le ratio doit être 1:8, 1:16 ou 1:32")
    private String ratio;

    @NotBlank(message = "Le répartiteur est obligatoire")
    @Indexed
    private String repartiteurId;

    private int nbSorties;

    @Builder.Default
    private int sortiesUtilisees = 0;

    @NotNull private Double latitude;
    @NotNull private Double longitude;

    @Builder.Default
    private String statut = "actif";

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}