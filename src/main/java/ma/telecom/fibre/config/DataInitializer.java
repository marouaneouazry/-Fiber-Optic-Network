package ma.telecom.fibre.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.telecom.fibre.models.*;
import ma.telecom.fibre.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final DatacenterRepository datacenterRepository;
    private final RepartiteurRepository repartiteurRepository;
    private final EquipementRepository equipementRepository;
    private final SplitterRepository splitterRepository;
    private final BoiteClientRepository boiteClientRepository;
    private final CheminFibreRepository cheminFibreRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            // ─── Ne rien faire si les données existent déjà ───
            if (userRepository.count() > 0) {
                log.info("✅ Base de données déjà initialisée — skip.");
                return;
            }

            log.info("🚀 Initialisation des données de test...");

            // ══════════════════════════════════════════
            // 1. UTILISATEURS
            // ══════════════════════════════════════════
            User admin = userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@telecom.ma")
                    .role(Role.ADMIN)
                    .build());

            userRepository.save(User.builder()
                    .username("technicien1")
                    .password(passwordEncoder.encode("tech123"))
                    .email("tech1@telecom.ma")
                    .role(Role.TECHNICIEN)
                    .build());

            log.info("👤 Utilisateurs créés : admin / technicien1");

            // ══════════════════════════════════════════
            // 2. DATACENTERS
            // ══════════════════════════════════════════
            Datacenter dc1 = datacenterRepository.save(Datacenter.builder()
                    .nom("DC Casablanca Centre")
                    .localisation("Bd Mohammed V, Casablanca")
                    .capacite(1000)
                    .latitude(33.5731)
                    .longitude(-7.5898)
                    .statut("actif")
                    .build());

            Datacenter dc2 = datacenterRepository.save(Datacenter.builder()
                    .nom("DC Casablanca Ain Sebaâ")
                    .localisation("Zone Industrielle Ain Sebaâ, Casablanca")
                    .capacite(500)
                    .latitude(33.6050)
                    .longitude(-7.5200)
                    .statut("actif")
                    .build());

            log.info("🏢 Datacenters créés : {} / {}", dc1.getNom(), dc2.getNom());

            // ══════════════════════════════════════════
            // 3. RÉPARTITEURS OLT
            // ══════════════════════════════════════════
            Repartiteur olt1 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-CASA-01")
                    .datacenterId(dc1.getId())
                    .nbPorts(64)
                    .portsUtilises(12)
                    .latitude(33.5800)
                    .longitude(-7.6050)
                    .statut("actif")
                    .build());

            Repartiteur olt2 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-CASA-02")
                    .datacenterId(dc1.getId())
                    .nbPorts(64)
                    .portsUtilises(8)
                    .latitude(33.5650)
                    .longitude(-7.6200)
                    .statut("actif")
                    .build());

            Repartiteur olt3 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-AIN-01")
                    .datacenterId(dc2.getId())
                    .nbPorts(32)
                    .portsUtilises(5)
                    .latitude(33.6000)
                    .longitude(-7.5300)
                    .statut("maintenance")
                    .build());

            log.info("📡 Répartiteurs OLT créés : {}, {}, {}", olt1.getNom(), olt2.getNom(), olt3.getNom());

            // ══════════════════════════════════════════
            // 4. ÉQUIPEMENTS ACTIFS
            // ══════════════════════════════════════════
            equipementRepository.save(Equipement.builder()
                    .type("OLT")
                    .ip("192.168.1.10")
                    .marque("Huawei")
                    .modele("MA5608T")
                    .emplacement("Rack A - U4")
                    .repartiteurId(olt1.getId())
                    .statut("actif")
                    .build());

            equipementRepository.save(Equipement.builder()
                    .type("Switch")
                    .ip("192.168.1.20")
                    .marque("Cisco")
                    .modele("Catalyst 2960")
                    .emplacement("Rack A - U6")
                    .repartiteurId(olt1.getId())
                    .statut("actif")
                    .build());

            equipementRepository.save(Equipement.builder()
                    .type("OLT")
                    .ip("192.168.2.10")
                    .marque("ZTE")
                    .modele("C320")
                    .emplacement("Rack B - U2")
                    .repartiteurId(olt2.getId())
                    .statut("coupe")
                    .build());

            log.info("⚙️  Équipements créés");

            // ══════════════════════════════════════════
            // 5. SPLITTERS
            // ══════════════════════════════════════════
            Splitter sp1 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16")
                    .repartiteurId(olt1.getId())
                    .nbSorties(16)
                    .sortiesUtilisees(9)
                    .latitude(33.5850)
                    .longitude(-7.6100)
                    .statut("actif")
                    .build());

            Splitter sp2 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8")
                    .repartiteurId(olt1.getId())
                    .nbSorties(8)
                    .sortiesUtilisees(3)
                    .latitude(33.5820)
                    .longitude(-7.6000)
                    .statut("actif")
                    .build());

            Splitter sp3 = splitterRepository.save(Splitter.builder()
                    .ratio("1:32")
                    .repartiteurId(olt2.getId())
                    .nbSorties(32)
                    .sortiesUtilisees(14)
                    .latitude(33.5700)
                    .longitude(-7.6300)
                    .statut("actif")
                    .build());

            log.info("🔀 Splitters créés : {}, {}, {}", sp1.getRatio(), sp2.getRatio(), sp3.getRatio());

            // ══════════════════════════════════════════
            // 6. BOÎTES CLIENTS
            // ══════════════════════════════════════════
            BoiteClient boite1 = BoiteClient.builder()
                    .type("16FO")
                    .splitterId(sp1.getId())
                    .capacite(16)
                    .portsUtilises(3)
                    .latitude(33.5870)
                    .longitude(-7.6150)
                    .statut("actif")
                    .build();
            boite1.setClients(List.of(
                    new BoiteClient.ClientPort(1, "Ahmed Benali",  "FTH-2024-001"),
                    new BoiteClient.ClientPort(2, "Sara Alaoui",   "FTH-2024-002"),
                    new BoiteClient.ClientPort(3, "Karim Tazi",    "FTH-2024-003")
            ));
            boiteClientRepository.save(boite1);

            BoiteClient boite2 = BoiteClient.builder()
                    .type("24FO")
                    .splitterId(sp1.getId())
                    .capacite(24)
                    .portsUtilises(2)
                    .latitude(33.5890)
                    .longitude(-7.6120)
                    .statut("actif")
                    .build();
            boite2.setClients(List.of(
                    new BoiteClient.ClientPort(1, "Fatima Zahra El Idrissi", "FTH-2024-004"),
                    new BoiteClient.ClientPort(2, "Youssef Mansouri",        "FTH-2024-005")
            ));
            boiteClientRepository.save(boite2);

            BoiteClient boite3 = BoiteClient.builder()
                    .type("16FO")
                    .splitterId(sp2.getId())
                    .capacite(16)
                    .portsUtilises(0)
                    .latitude(33.5830)
                    .longitude(-7.6020)
                    .statut("maintenance")
                    .build();
            boiteClientRepository.save(boite3);

            log.info("📦 Boîtes clients créées");

            // ══════════════════════════════════════════
            // 7. CHEMINS FIBRE
            // ══════════════════════════════════════════

            // DC1 → OLT1
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc1.getId()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt1.getId()))
                    .longueur("1.2km")
                    .typeFibre("SMF G.652D")
                    .statut("actif")
                    .coordonnees(List.of(
                            new CheminFibre.Coordonnee(1, 33.5731, -7.5898),
                            new CheminFibre.Coordonnee(2, 33.5760, -7.5970),
                            new CheminFibre.Coordonnee(3, 33.5800, -7.6050)
                    ))
                    .build());

            // DC1 → OLT2
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc1.getId()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt2.getId()))
                    .longueur("2.1km")
                    .typeFibre("SMF G.652D")
                    .statut("actif")
                    .coordonnees(List.of(
                            new CheminFibre.Coordonnee(1, 33.5731, -7.5898),
                            new CheminFibre.Coordonnee(2, 33.5690, -7.6050),
                            new CheminFibre.Coordonnee(3, 33.5650, -7.6200)
                    ))
                    .build());

            // OLT1 → Splitter1
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt1.getId()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp1.getId()))
                    .longueur("0.5km")
                    .typeFibre("SMF G.657A")
                    .statut("actif")
                    .coordonnees(List.of(
                            new CheminFibre.Coordonnee(1, 33.5800, -7.6050),
                            new CheminFibre.Coordonnee(2, 33.5825, -7.6075),
                            new CheminFibre.Coordonnee(3, 33.5850, -7.6100)
                    ))
                    .build());

            // OLT1 → Splitter2
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt1.getId()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp2.getId()))
                    .longueur("0.3km")
                    .typeFibre("SMF G.657A")
                    .statut("actif")
                    .coordonnees(List.of(
                            new CheminFibre.Coordonnee(1, 33.5800, -7.6050),
                            new CheminFibre.Coordonnee(2, 33.5810, -7.6025),
                            new CheminFibre.Coordonnee(3, 33.5820, -7.6000)
                    ))
                    .build());

            // Splitter1 → Boite1
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp1.getId()))
                    .destination(new CheminFibre.NoeudRef("boite", boite1.getId()))
                    .longueur("0.15km")
                    .typeFibre("SMF G.657A2")
                    .statut("actif")
                    .coordonnees(List.of(
                            new CheminFibre.Coordonnee(1, 33.5850, -7.6100),
                            new CheminFibre.Coordonnee(2, 33.5860, -7.6125),
                            new CheminFibre.Coordonnee(3, 33.5870, -7.6150)
                    ))
                    .build());

            // DC2 → OLT3 (maintenance)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc2.getId()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt3.getId()))
                    .longueur("0.8km")
                    .typeFibre("SMF G.652D")
                    .statut("maintenance")
                    .coordonnees(List.of(
                            new CheminFibre.Coordonnee(1, 33.6050, -7.5200),
                            new CheminFibre.Coordonnee(2, 33.6025, -7.5250),
                            new CheminFibre.Coordonnee(3, 33.6000, -7.5300)
                    ))
                    .build());

            log.info("🔗 Chemins fibre créés");

            // ══════════════════════════════════════════
            // RÉSUMÉ
            // ══════════════════════════════════════════
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.info("✅ Initialisation terminée !");
            log.info("   👤 Users        : {}", userRepository.count());
            log.info("   🏢 Datacenters  : {}", datacenterRepository.count());
            log.info("   📡 Répartiteurs : {}", repartiteurRepository.count());
            log.info("   ⚙️  Équipements  : {}", equipementRepository.count());
            log.info("   🔀 Splitters    : {}", splitterRepository.count());
            log.info("   📦 Boîtes       : {}", boiteClientRepository.count());
            log.info("   🔗 Chemins      : {}", cheminFibreRepository.count());
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.info("🔑 Identifiants de connexion :");
            log.info("   Admin      → admin / admin123");
            log.info("   Technicien → technicien1 / tech123");
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        };
    }
}