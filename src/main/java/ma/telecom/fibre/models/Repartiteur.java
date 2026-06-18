package ma.telecom.fibre.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "repartiteurs")
public class Repartiteur {

    @Id
    private String id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    // RG01 : doit être lié à un Datacenter
    @NotBlank(message = "Le datacenter est obligatoire")
    @Indexed
    private String datacenterId;

    private int nbPorts;

    @Builder.Default
    private int portsUtilises = 0;

    @NotNull private Double latitude;
    @NotNull private Double longitude;

    @Builder.Default
    private String statut = "actif";

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}