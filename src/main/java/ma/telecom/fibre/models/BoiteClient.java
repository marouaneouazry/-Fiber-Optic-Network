package ma.telecom.fibre.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "boites_clients")
public class BoiteClient {

    @Id
    private String id;

    @NotBlank
    private String type; // 16FO | 24FO

    // RG03 : doit être liée à un Splitter
    @NotBlank(message = "Le splitter est obligatoire")
    @Indexed
    private String splitterId;

    private int capacite; // 16 ou 24

    @Builder.Default
    private int portsUtilises = 0;

    @NotNull private Double latitude;
    @NotNull private Double longitude;

    @Builder.Default
    private String statut = "actif";

    // Clients connectés sur les ports
    @Builder.Default
    private List<ClientPort> clients = new ArrayList<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Classe imbriquée pour les clients sur chaque port
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ClientPort {
        private int port;
        private String nom;
        private String contrat;
    }
}