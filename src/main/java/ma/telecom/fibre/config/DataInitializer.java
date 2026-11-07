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
            if (userRepository.count() > 0) {
                log.info("✅ Base de données déjà initialisée — skip.");
                return;
            }

            log.info("🚀 Initialisation des données réalistes Casablanca FTTH...");

            // ══════════════════════════════════════════
            // 1. UTILISATEURS
            // ══════════════════════════════════════════
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@telecom.ma")
                    .role(Role.ADMIN).build());

            userRepository.save(User.builder()
                    .username("technicien1")
                    .password(passwordEncoder.encode("tech123"))
                    .email("tech1@telecom.ma")
                    .role(Role.TECHNICIEN).build());

            userRepository.save(User.builder()
                    .username("technicien2")
                    .password(passwordEncoder.encode("tech123"))
                    .email("tech2@telecom.ma")
                    .role(Role.TECHNICIEN).build());

            log.info("👤 3 utilisateurs créés");

            // ══════════════════════════════════════════
            // 2. DATACENTERS — 3 zones réelles Casablanca
            // ══════════════════════════════════════════

            // Zone 1 : Centre-ville (Orange Maroc / Maroc Telecom)
            Datacenter dc1 = datacenterRepository.save(Datacenter.builder()
                    .nom("DC Casa Centre — Bd Mohammed V")
                    .localisation("Bd Mohammed V, Casablanca 20000")
                    .capacite(1200)
                    .latitude(33.5928)
                    .longitude(-7.6192)
                    .statut("actif").build());

            // Zone 2 : Ain Sebaâ industriel (Inwi)
            Datacenter dc2 = datacenterRepository.save(Datacenter.builder()
                    .nom("DC Ain Sebaâ — Zone Industrielle")
                    .localisation("Zone Industrielle Ain Sebaâ, Casablanca")
                    .capacite(800)
                    .latitude(33.6078)
                    .longitude(-7.5156)
                    .statut("actif").build());

            // Zone 3 : Sidi Maarouf (Technopark / N+ONE)
            Datacenter dc3 = datacenterRepository.save(Datacenter.builder()
                    .nom("DC Sidi Maarouf — Technopark")
                    .localisation("Technopark Casablanca, Route de Nouaceur")
                    .capacite(600)
                    .latitude(33.5274)
                    .longitude(-7.6431)
                    .statut("actif").build());

            log.info("🏢 3 Datacenters créés (Centre / Ain Sebaâ / Sidi Maarouf)");

            // ══════════════════════════════════════════
            // 3. RÉPARTITEURS OLT — 8 au total
            // ══════════════════════════════════════════

            // DC1 → 3 OLT couvrant Centre/Maarif/Gauthier
            Repartiteur olt1 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-CENTRE-01 (Maarif)")
                    .datacenterId(dc1.getId())
                    .nbPorts(64).portsUtilises(48)
                    .latitude(33.5820).longitude(-7.6350)
                    .statut("actif").build());

            Repartiteur olt2 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-CENTRE-02 (Gauthier)")
                    .datacenterId(dc1.getId())
                    .nbPorts(64).portsUtilises(31)
                    .latitude(33.5980).longitude(-7.6380)
                    .statut("actif").build());

            Repartiteur olt3 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-CENTRE-03 (Racine)")
                    .datacenterId(dc1.getId())
                    .nbPorts(32).portsUtilises(29)
                    .latitude(33.5900).longitude(-7.6500)
                    .statut("maintenance").build());

            // DC2 → 3 OLT couvrant Ain Sebaâ/Hay Mohammadi
            Repartiteur olt4 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-AIN-01 (Ain Sebaâ Nord)")
                    .datacenterId(dc2.getId())
                    .nbPorts(64).portsUtilises(22)
                    .latitude(33.6150).longitude(-7.5200)
                    .statut("actif").build());

            Repartiteur olt5 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-AIN-02 (Hay Mohammadi)")
                    .datacenterId(dc2.getId())
                    .nbPorts(64).portsUtilises(55)
                    .latitude(33.5950).longitude(-7.5400)
                    .statut("actif").build());

            Repartiteur olt6 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-AIN-03 (Bernoussi)")
                    .datacenterId(dc2.getId())
                    .nbPorts(32).portsUtilises(8)
                    .latitude(33.6250).longitude(-7.5050)
                    .statut("coupe").build());

            // DC3 → 2 OLT couvrant Sidi Maarouf/Bouskoura
            Repartiteur olt7 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-SID-01 (Sidi Maarouf)")
                    .datacenterId(dc3.getId())
                    .nbPorts(64).portsUtilises(41)
                    .latitude(33.5350).longitude(-7.6550)
                    .statut("actif").build());

            Repartiteur olt8 = repartiteurRepository.save(Repartiteur.builder()
                    .nom("OLT-SID-02 (Bouskoura)")
                    .datacenterId(dc3.getId())
                    .nbPorts(32).portsUtilises(17)
                    .latitude(33.4980).longitude(-7.6620)
                    .statut("actif").build());

            log.info("📡 8 Répartiteurs OLT créés");

            // ══════════════════════════════════════════
            // 4. ÉQUIPEMENTS ACTIFS — 8 équipements
            // ══════════════════════════════════════════
            equipementRepository.save(Equipement.builder()
                    .type("OLT").ip("10.0.1.10")
                    .marque("Huawei").modele("MA5608T")
                    .emplacement("Rack A - U4").repartiteurId(olt1.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("Switch").ip("10.0.1.20")
                    .marque("Cisco").modele("Catalyst 9300")
                    .emplacement("Rack A - U8").repartiteurId(olt1.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("OLT").ip("10.0.2.10")
                    .marque("ZTE").modele("C320")
                    .emplacement("Rack B - U2").repartiteurId(olt2.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("Routeur").ip("10.0.2.1")
                    .marque("Juniper").modele("MX204")
                    .emplacement("Rack B - U1").repartiteurId(olt2.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("OLT").ip("10.0.3.10")
                    .marque("Huawei").modele("MA5800-X7")
                    .emplacement("Rack C - U4").repartiteurId(olt4.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("Switch").ip("10.0.4.20")
                    .marque("Cisco").modele("Catalyst 2960")
                    .emplacement("Rack D - U6").repartiteurId(olt5.getId())
                    .statut("coupe").build());

            equipementRepository.save(Equipement.builder()
                    .type("OLT").ip("10.0.5.10")
                    .marque("Nokia").modele("7360 ISAM FX")
                    .emplacement("Rack E - U2").repartiteurId(olt7.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("Routeur").ip("10.0.5.1")
                    .marque("Cisco").modele("ASR 1001-X")
                    .emplacement("Rack E - U1").repartiteurId(olt7.getId())
                    .statut("maintenance").build());

            // OLT3 (Racine) — équipements
            equipementRepository.save(Equipement.builder()
                    .type("OLT").ip("10.0.3.30")
                    .marque("Huawei").modele("MA5600T")
                    .emplacement("Rack C - U6").repartiteurId(olt3.getId())
                    .statut("maintenance").build());

            equipementRepository.save(Equipement.builder()
                    .type("ODF").ip("10.0.3.31")
                    .marque("Corning").modele("CCH-CP12-A9")
                    .emplacement("Rack C - U8").repartiteurId(olt3.getId())
                    .statut("maintenance").build());

            // OLT6 (Bernoussi) — équipements
            equipementRepository.save(Equipement.builder()
                    .type("OLT").ip("10.0.6.10")
                    .marque("ZTE").modele("C600")
                    .emplacement("Rack F - U2").repartiteurId(olt6.getId())
                    .statut("coupe").build());

            // OLT8 (Bouskoura) — amplificateur EDFA
            equipementRepository.save(Equipement.builder()
                    .type("EDFA").ip("10.0.8.10")
                    .marque("Huawei").modele("OSN 1800V")
                    .emplacement("Rack H - U3").repartiteurId(olt8.getId())
                    .statut("actif").build());

            // OLT5 (Hay Mohammadi) — ROADM et ODF
            equipementRepository.save(Equipement.builder()
                    .type("ROADM").ip("10.0.4.50")
                    .marque("Ciena").modele("6500 Packet-Optical")
                    .emplacement("Rack D - U2").repartiteurId(olt5.getId())
                    .statut("actif").build());

            equipementRepository.save(Equipement.builder()
                    .type("ODF").ip("10.0.4.51")
                    .marque("CommScope").modele("InstaPatch 360")
                    .emplacement("Rack D - U4").repartiteurId(olt5.getId())
                    .statut("actif").build());

            log.info("⚙️  14 Équipements créés");

            // ══════════════════════════════════════════
            // 5. SPLITTERS — 12 splitters
            // ══════════════════════════════════════════

            // OLT1 (Maarif) → 3 splitters
            Splitter sp1 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt1.getId())
                    .nbSorties(16).sortiesUtilisees(14)
                    .latitude(33.5780).longitude(-7.6400)
                    .statut("actif").build());

            Splitter sp2 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8").repartiteurId(olt1.getId())
                    .nbSorties(8).sortiesUtilisees(8)
                    .latitude(33.5850).longitude(-7.6420)
                    .statut("actif").build());

            Splitter sp3 = splitterRepository.save(Splitter.builder()
                    .ratio("1:32").repartiteurId(olt1.getId())
                    .nbSorties(32).sortiesUtilisees(19)
                    .latitude(33.5760).longitude(-7.6300)
                    .statut("actif").build());

            // OLT2 (Gauthier) → 2 splitters
            Splitter sp4 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt2.getId())
                    .nbSorties(16).sortiesUtilisees(7)
                    .latitude(33.5990).longitude(-7.6420)
                    .statut("actif").build());

            Splitter sp5 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8").repartiteurId(olt2.getId())
                    .nbSorties(8).sortiesUtilisees(3)
                    .latitude(33.6010).longitude(-7.6350)
                    .statut("maintenance").build());

            // OLT4 (Ain Sebaâ Nord) → 2 splitters
            Splitter sp6 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt4.getId())
                    .nbSorties(16).sortiesUtilisees(10)
                    .latitude(33.6180).longitude(-7.5250)
                    .statut("actif").build());

            Splitter sp7 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8").repartiteurId(olt4.getId())
                    .nbSorties(8).sortiesUtilisees(4)
                    .latitude(33.6120).longitude(-7.5180)
                    .statut("actif").build());

            // OLT5 (Hay Mohammadi) → 2 splitters
            Splitter sp8 = splitterRepository.save(Splitter.builder()
                    .ratio("1:32").repartiteurId(olt5.getId())
                    .nbSorties(32).sortiesUtilisees(28)
                    .latitude(33.5920).longitude(-7.5450)
                    .statut("actif").build());

            Splitter sp9 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt5.getId())
                    .nbSorties(16).sortiesUtilisees(16)
                    .latitude(33.5980).longitude(-7.5380)
                    .statut("actif").build());

            // OLT7 (Sidi Maarouf) → 2 splitters
            Splitter sp10 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt7.getId())
                    .nbSorties(16).sortiesUtilisees(11)
                    .latitude(33.5380).longitude(-7.6600)
                    .statut("actif").build());

            Splitter sp11 = splitterRepository.save(Splitter.builder()
                    .ratio("1:32").repartiteurId(olt7.getId())
                    .nbSorties(32).sortiesUtilisees(22)
                    .latitude(33.5320).longitude(-7.6500)
                    .statut("actif").build());

            // OLT8 (Bouskoura) → 1 splitter
            Splitter sp12 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8").repartiteurId(olt8.getId())
                    .nbSorties(8).sortiesUtilisees(5)
                    .latitude(33.4950).longitude(-7.6650)
                    .statut("actif").build());

            // OLT3 (Racine) → 2 splitters
            Splitter sp13 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8").repartiteurId(olt3.getId())
                    .nbSorties(8).sortiesUtilisees(6)
                    .latitude(33.5885).longitude(-7.6510)
                    .statut("maintenance").build());

            Splitter sp14 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt3.getId())
                    .nbSorties(16).sortiesUtilisees(4)
                    .latitude(33.5915).longitude(-7.6490)
                    .statut("maintenance").build());

            // OLT6 (Bernoussi) → 2 splitters
            Splitter sp15 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt6.getId())
                    .nbSorties(16).sortiesUtilisees(0)
                    .latitude(33.6240).longitude(-7.5060)
                    .statut("coupe").build());

            Splitter sp16 = splitterRepository.save(Splitter.builder()
                    .ratio("1:8").repartiteurId(olt6.getId())
                    .nbSorties(8).sortiesUtilisees(2)
                    .latitude(33.6260).longitude(-7.5040)
                    .statut("coupe").build());

            // OLT7 (Sidi Maarouf) — sp11 existe déjà ; on ajoute 1 splitter supplémentaire 1:32 haute densité
            Splitter sp17 = splitterRepository.save(Splitter.builder()
                    .ratio("1:32").repartiteurId(olt7.getId())
                    .nbSorties(32).sortiesUtilisees(30)
                    .latitude(33.5330).longitude(-7.6530)
                    .statut("actif").build());

            // OLT8 (Bouskoura) — splitter secondaire 1:16
            Splitter sp18 = splitterRepository.save(Splitter.builder()
                    .ratio("1:16").repartiteurId(olt8.getId())
                    .nbSorties(16).sortiesUtilisees(9)
                    .latitude(33.4970).longitude(-7.6635)
                    .statut("actif").build());

            log.info("🔀 18 Splitters créés");

            // ══════════════════════════════════════════
            // 6. BOÎTES CLIENTS — 15 boîtes
            // ══════════════════════════════════════════

            // sp1 (Maarif 1:16) → 3 boîtes
            BoiteClient b1 = BoiteClient.builder()
                    .type("16FO").splitterId(sp1.getId())
                    .capacite(16).portsUtilises(12)
                    .latitude(33.5765).longitude(-7.6430)
                    .statut("actif").build();
            b1.setClients(List.of(
                new BoiteClient.ClientPort(1,  "Ahmed Benali",           "FTH-2024-001", "0661000001", "Maarif", "actif"),
                new BoiteClient.ClientPort(2,  "Sara Alaoui",            "FTH-2024-002", "0661000002", "Maarif", "actif"),
                new BoiteClient.ClientPort(3,  "Karim Tazi",             "FTH-2024-003", "0661000003", "Maarif", "actif"),
                new BoiteClient.ClientPort(4,  "Nadia El Fassi",         "FTH-2024-004", "0661000004", "Maarif", "actif"),
                new BoiteClient.ClientPort(5,  "Omar Benjelloun",        "FTH-2024-005", "0661000005", "Maarif", "actif"),
                new BoiteClient.ClientPort(6,  "Leila Chraibi",          "FTH-2024-006", "0661000006", "Maarif", "actif"),
                new BoiteClient.ClientPort(7,  "Hassan Berrada",         "FTH-2024-007", "0661000007", "Maarif", "actif"),
                new BoiteClient.ClientPort(8,  "Fatima Zahra Lahlou",   "FTH-2024-008", "0661000008", "Maarif", "actif"),
                new BoiteClient.ClientPort(9,  "Youssef Kettani",        "FTH-2024-009", "0661000009", "Maarif", "actif"),
                new BoiteClient.ClientPort(10, "Amina Benhaddou",        "FTH-2024-010", "0661000010", "Maarif", "actif"),
                new BoiteClient.ClientPort(11, "Rachid Squalli",         "FTH-2024-011", "0661000011", "Maarif", "actif"),
                new BoiteClient.ClientPort(12, "Zineb Moussaoui",        "FTH-2024-012", "0661000012", "Maarif", "actif")
            ));
            boiteClientRepository.save(b1);

            BoiteClient b2 = BoiteClient.builder()
                    .type("24FO").splitterId(sp1.getId())
                    .capacite(24).portsUtilises(9)
                    .latitude(33.5790).longitude(-7.6415)
                    .statut("actif").build();
            b2.setClients(List.of(
                new BoiteClient.ClientPort(1, "Mohammed Alami",     "FTH-2024-013", "0662000001", "Maarif", "actif"),
                new BoiteClient.ClientPort(2, "Soukaina Tahiri",    "FTH-2024-014", "0662000002", "Maarif", "actif"),
                new BoiteClient.ClientPort(3, "Bilal Ghazali",      "FTH-2024-015", "0662000003", "Maarif", "actif"),
                new BoiteClient.ClientPort(4, "Houda Mansouri",     "FTH-2024-016", "0662000004", "Maarif", "actif"),
                new BoiteClient.ClientPort(5, "Anas El Idrissi",    "FTH-2024-017", "0662000005", "Maarif", "actif"),
                new BoiteClient.ClientPort(6, "Rim Bensouda",       "FTH-2024-018", "0662000006", "Maarif", "actif"),
                new BoiteClient.ClientPort(7, "Adil Cherkaoui",     "FTH-2024-019", "0662000007", "Maarif", "actif"),
                new BoiteClient.ClientPort(8, "Kenza Filali",       "FTH-2024-020", "0662000008", "Maarif", "actif"),
                new BoiteClient.ClientPort(9, "Tarik Benkirane",    "FTH-2024-021", "0662000009", "Maarif", "actif")
            ));
            boiteClientRepository.save(b2);

            BoiteClient b3 = BoiteClient.builder()
                    .type("16FO").splitterId(sp1.getId())
                    .capacite(16).portsUtilises(0)
                    .latitude(33.5775).longitude(-7.6445)
                    .statut("maintenance").build();
            boiteClientRepository.save(b3);

            // sp2 (Maarif 1:8) → 2 boîtes
            BoiteClient b4 = BoiteClient.builder()
                    .type("16FO").splitterId(sp2.getId())
                    .capacite(16).portsUtilises(16)
                    .latitude(33.5860).longitude(-7.6440)
                    .statut("actif").build();
            b4.setClients(List.of(
                new BoiteClient.ClientPort(1, "Samira Ouali",      "FTH-2024-022", "0663000001", "Maarif", "actif"),
                new BoiteClient.ClientPort(2, "Driss Bakkali",     "FTH-2024-023", "0663000002", "Maarif", "actif"),
                new BoiteClient.ClientPort(3, "Meriem Tlemcani",   "FTH-2024-024", "0663000003", "Maarif", "actif"),
                new BoiteClient.ClientPort(4, "Khalid Sabri",      "FTH-2024-025", "0663000004", "Maarif", "actif"),
                new BoiteClient.ClientPort(5, "Widad Naciri",      "FTH-2024-026", "0663000005", "Maarif", "actif"),
                new BoiteClient.ClientPort(6, "Hamza El Ouafi",    "FTH-2024-027", "0663000006", "Maarif", "actif"),
                new BoiteClient.ClientPort(7, "Sanaa Berrada",     "FTH-2024-028", "0663000007", "Maarif", "actif"),
                new BoiteClient.ClientPort(8, "Ayoub Laaroussi",   "FTH-2024-029", "0663000008", "Maarif", "actif"),
                new BoiteClient.ClientPort(9, "Dounia Zouaki",     "FTH-2024-030", "0663000009", "Maarif", "actif"),
                new BoiteClient.ClientPort(10,"Imane Belfqih",     "FTH-2024-031", "0663000010", "Maarif", "actif"),
                new BoiteClient.ClientPort(11,"Yassine Hajji",     "FTH-2024-032", "0663000011", "Maarif", "actif"),
                new BoiteClient.ClientPort(12,"Hajar Mrani",       "FTH-2024-033", "0663000012", "Maarif", "actif"),
                new BoiteClient.ClientPort(13,"Ilyas Guessous",    "FTH-2024-034", "0663000013", "Maarif", "actif"),
                new BoiteClient.ClientPort(14,"Asmaa Rahhou",      "FTH-2024-035", "0663000014", "Maarif", "actif"),
                new BoiteClient.ClientPort(15,"Zakaria Bennis",    "FTH-2024-036", "0663000015", "Maarif", "actif"),
                new BoiteClient.ClientPort(16,"Chaymae El Fassi",  "FTH-2024-037", "0663000016", "Maarif", "actif")
            ));
            boiteClientRepository.save(b4);

            BoiteClient b5 = BoiteClient.builder()
                    .type("24FO").splitterId(sp2.getId())
                    .capacite(24).portsUtilises(5)
                    .latitude(33.5840).longitude(-7.6410)
                    .statut("actif").build();
            b5.setClients(List.of(
                new BoiteClient.ClientPort(1, "Ghita Sekkat",      "FTH-2024-038", "0664000001", "Maarif", "actif"),
                new BoiteClient.ClientPort(2, "Noureddine Fassi",  "FTH-2024-039", "0664000002", "Maarif", "actif"),
                new BoiteClient.ClientPort(3, "Layla Benkirane",   "FTH-2024-040", "0664000003", "Maarif", "actif"),
                new BoiteClient.ClientPort(4, "Mehdi Berrada",     "FTH-2024-041", "0664000004", "Maarif", "actif"),
                new BoiteClient.ClientPort(5, "Nora Benhima",      "FTH-2024-042", "0664000005", "Maarif", "actif")
            ));
            boiteClientRepository.save(b5);

            // sp4 (Gauthier 1:16) → 2 boîtes
            BoiteClient b6 = BoiteClient.builder()
                    .type("16FO").splitterId(sp4.getId())
                    .capacite(16).portsUtilises(7)
                    .latitude(33.5995).longitude(-7.6450)
                    .statut("actif").build();
            b6.setClients(List.of(
                new BoiteClient.ClientPort(1, "Samir Chami",       "FTH-2024-043", "0665000001", "Gauthier", "actif"),
                new BoiteClient.ClientPort(2, "Rania El Alami",    "FTH-2024-044", "0665000002", "Gauthier", "actif"),
                new BoiteClient.ClientPort(3, "Fouad Kettani",     "FTH-2024-045", "0665000003", "Gauthier", "actif"),
                new BoiteClient.ClientPort(4, "Malak Bensouda",    "FTH-2024-046", "0665000004", "Gauthier", "actif"),
                new BoiteClient.ClientPort(5, "Ismail Zniber",     "FTH-2024-047", "0665000005", "Gauthier", "actif"),
                new BoiteClient.ClientPort(6, "Hind Sqalli",       "FTH-2024-048", "0665000006", "Gauthier", "actif"),
                new BoiteClient.ClientPort(7, "Karim Tazi",        "FTH-2024-049", "0665000007", "Gauthier", "actif")
            ));
            boiteClientRepository.save(b6);

            BoiteClient b7 = BoiteClient.builder()
                    .type("24FO").splitterId(sp4.getId())
                    .capacite(24).portsUtilises(3)
                    .latitude(33.6005).longitude(-7.6430)
                    .statut("actif").build();
            b7.setClients(List.of(
                new BoiteClient.ClientPort(1, "Sophia Benali",     "FTH-2024-050", "0666000001", "Gauthier", "actif"),
                new BoiteClient.ClientPort(2, "Oussama Bakkali",   "FTH-2024-051", "0666000002", "Gauthier", "actif"),
                new BoiteClient.ClientPort(3, "Yasmine Fassi",     "FTH-2024-052", "0666000003", "Gauthier", "actif")
            ));
            boiteClientRepository.save(b7);

            // sp6 (Ain Sebaâ 1:16) → 2 boîtes
            BoiteClient b8 = BoiteClient.builder()
                    .type("16FO").splitterId(sp6.getId())
                    .capacite(16).portsUtilises(10)
                    .latitude(33.6190).longitude(-7.5270)
                    .statut("actif").build();
            b8.setClients(List.of(
                new BoiteClient.ClientPort(1,  "Abdellah Moujane",  "FTH-2024-053", "0667000001", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(2,  "Fatna Bouhali",     "FTH-2024-054", "0667000002", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(3,  "Mostafa El Khiari", "FTH-2024-055", "0667000003", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(4,  "Naima Senhaji",     "FTH-2024-056", "0667000004", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(5,  "Brahim Rhzioui",    "FTH-2024-057", "0667000005", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(6,  "Khadija Hmidouch",  "FTH-2024-058", "0667000006", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(7,  "Said Boumhidi",     "FTH-2024-059", "0667000007", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(8,  "Houria Benazza",    "FTH-2024-060", "0667000008", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(9,  "Mourad Lachkar",    "FTH-2024-061", "0667000009", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(10, "Aziza Ouazzani",    "FTH-2024-062", "0667000010", "Ain Sebaâ", "actif")
            ));
            boiteClientRepository.save(b8);

            BoiteClient b9 = BoiteClient.builder()
                    .type("16FO").splitterId(sp6.getId())
                    .capacite(16).portsUtilises(4)
                    .latitude(33.6170).longitude(-7.5230)
                    .statut("actif").build();
            b9.setClients(List.of(
                new BoiteClient.ClientPort(1, "Youssef Lahjouji",  "FTH-2024-063", "0668000001", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(2, "Aicha Tichiti",     "FTH-2024-064", "0668000002", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(3, "Rachid Ouali",      "FTH-2024-065", "0668000003", "Ain Sebaâ", "actif"),
                new BoiteClient.ClientPort(4, "Hayat Bouziane",    "FTH-2024-066", "0668000004", "Ain Sebaâ", "actif")
            ));
            boiteClientRepository.save(b9);

            // sp8 (Hay Mohammadi 1:32) → 2 boîtes
            BoiteClient b10 = BoiteClient.builder()
                    .type("24FO").splitterId(sp8.getId())
                    .capacite(24).portsUtilises(24)
                    .latitude(33.5930).longitude(-7.5480)
                    .statut("actif").build();
            b10.setClients(List.of(
                new BoiteClient.ClientPort(1,  "Lahcen Ouazzani",   "FTH-2024-067", "0669000001", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(2,  "Fatiha Belkebir",   "FTH-2024-068", "0669000002", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(3,  "Mustapha Zaki",     "FTH-2024-069", "0669000003", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(4,  "Zineb Rahmouni",    "FTH-2024-070", "0669000004", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(5,  "Aziz Bensouda",     "FTH-2024-071", "0669000005", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(6,  "Khaoula Idrissi",   "FTH-2024-072", "0669000006", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(7,  "Hamid Tahiri",      "FTH-2024-073", "0669000007", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(8,  "Nawal Chaouki",     "FTH-2024-074", "0669000008", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(9,  "Saad El Mansouri",  "FTH-2024-075", "0669000009", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(10, "Siham Ouariachi",   "FTH-2024-076", "0669000010", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(11, "Jamal Benkiran",    "FTH-2024-077", "0669000011", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(12, "Loubna Merroun",    "FTH-2024-078", "0669000012", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(13, "Abdessamad Filali", "FTH-2024-079", "0669000013", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(14, "Ghizlane Essabbar", "FTH-2024-080", "0669000014", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(15, "Redouane Badr",     "FTH-2024-081", "0669000015", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(16, "Wafaa Benkirane",   "FTH-2024-082", "0669000016", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(17, "Taoufik Hajji",     "FTH-2024-083", "0669000017", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(18, "Nisrine Belhaj",    "FTH-2024-084", "0669000018", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(19, "Hicham Labriki",    "FTH-2024-085", "0669000019", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(20, "Oumaima Chraibi",   "FTH-2024-086", "0669000020", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(21, "Mounir Zriouli",    "FTH-2024-087", "0669000021", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(22, "Ilham Benabdallah", "FTH-2024-088", "0669000022", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(23, "Soufiane Ghali",    "FTH-2024-089", "0669000023", "Hay Mohammadi", "actif"),
                new BoiteClient.ClientPort(24, "Manal Bouzid",      "FTH-2024-090", "0669000024", "Hay Mohammadi", "actif")
            ));
            boiteClientRepository.save(b10);

            BoiteClient b11 = BoiteClient.builder()
                    .type("16FO").splitterId(sp8.getId())
                    .capacite(16).portsUtilises(0)
                    .latitude(33.5910).longitude(-7.5420)
                    .statut("actif").build();
            boiteClientRepository.save(b11);

            // sp10 (Sidi Maarouf 1:16) → 2 boîtes
            BoiteClient b12 = BoiteClient.builder()
                    .type("24FO").splitterId(sp10.getId())
                    .capacite(24).portsUtilises(0)
                    .latitude(33.5390).longitude(-7.6620)
                    .statut("actif").build();
            boiteClientRepository.save(b12);

            BoiteClient b13 = BoiteClient.builder()
                    .type("16FO").splitterId(sp10.getId())
                    .capacite(16).portsUtilises(0)
                    .latitude(33.5370).longitude(-7.6580)
                    .statut("coupe").build();
            boiteClientRepository.save(b13);

            // sp12 (Bouskoura 1:8) → 2 boîtes
            BoiteClient b14 = BoiteClient.builder()
                    .type("16FO").splitterId(sp12.getId())
                    .capacite(16).portsUtilises(0)
                    .latitude(33.4960).longitude(-7.6670)
                    .statut("actif").build();
            boiteClientRepository.save(b14);

            BoiteClient b15 = BoiteClient.builder()
                    .type("24FO").splitterId(sp12.getId())
                    .capacite(24).portsUtilises(0)
                    .latitude(33.4940).longitude(-7.6640)
                    .statut("actif").build();
            boiteClientRepository.save(b15);

            // sp13 (Racine 1:8) → 1 boîte
            BoiteClient b16 = BoiteClient.builder()
                    .type("16FO").splitterId(sp13.getId())
                    .capacite(16).portsUtilises(6)
                    .latitude(33.5880).longitude(-7.6525)
                    .statut("maintenance").build();
            b16.setClients(List.of(
                new BoiteClient.ClientPort(1, "Zakaria Mernissi",   "FTH-2025-091", "0670000001", "Racine", "maintenance"),
                new BoiteClient.ClientPort(2, "Mouna Cherkaoui",    "FTH-2025-092", "0670000002", "Racine", "maintenance"),
                new BoiteClient.ClientPort(3, "Khalid Hafidi",      "FTH-2025-093", "0670000003", "Racine", "maintenance"),
                new BoiteClient.ClientPort(4, "Yasmine Kettani",    "FTH-2025-094", "0670000004", "Racine", "maintenance"),
                new BoiteClient.ClientPort(5, "Amine Senhaji",      "FTH-2025-095", "0670000005", "Racine", "maintenance"),
                new BoiteClient.ClientPort(6, "Rima Lahlou",        "FTH-2025-096", "0670000006", "Racine", "maintenance")
            ));
            boiteClientRepository.save(b16);

            // sp14 (Racine 1:16) → 1 boîte
            BoiteClient b17 = BoiteClient.builder()
                    .type("24FO").splitterId(sp14.getId())
                    .capacite(24).portsUtilises(4)
                    .latitude(33.5920).longitude(-7.6500)
                    .statut("maintenance").build();
            b17.setClients(List.of(
                new BoiteClient.ClientPort(1, "Ibtissam Ziani",     "FTH-2025-097", "0671000001", "Racine", "maintenance"),
                new BoiteClient.ClientPort(2, "Nabil Benchekroun",  "FTH-2025-098", "0671000002", "Racine", "maintenance"),
                new BoiteClient.ClientPort(3, "Hasnaa Berrada",     "FTH-2025-099", "0671000003", "Racine", "maintenance"),
                new BoiteClient.ClientPort(4, "Marouane El Fassi",  "FTH-2025-100", "0671000004", "Racine", "maintenance")
            ));
            boiteClientRepository.save(b17);

            // sp17 (Sidi Maarouf 1:32 haute densité) → 2 boîtes
            BoiteClient b18 = BoiteClient.builder()
                    .type("24FO").splitterId(sp17.getId())
                    .capacite(24).portsUtilises(22)
                    .latitude(33.5325).longitude(-7.6545)
                    .statut("actif").build();
            b18.setClients(List.of(
                new BoiteClient.ClientPort(1,  "Safae El Amrani",   "FTH-2025-101", "0672000001", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(2,  "Tarik Ouahbi",      "FTH-2025-102", "0672000002", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(3,  "Loubna Zriouli",    "FTH-2025-103", "0672000003", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(4,  "Hicham Alaoui",     "FTH-2025-104", "0672000004", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(5,  "Nadia Benkabbou",   "FTH-2025-105", "0672000005", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(6,  "Yassir Ghallab",    "FTH-2025-106", "0672000006", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(7,  "Soukaina Kadiri",   "FTH-2025-107", "0672000007", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(8,  "Anas Benkirane",    "FTH-2025-108", "0672000008", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(9,  "Rim Chaoui",        "FTH-2025-109", "0672000009", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(10, "Mehdi Squalli",     "FTH-2025-110", "0672000010", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(11, "Kawtar Tazi",       "FTH-2025-111", "0672000011", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(12, "Bilal Moussaoui",   "FTH-2025-112", "0672000012", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(13, "Zineb Rhzioui",     "FTH-2025-113", "0672000013", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(14, "Adil Mansouri",     "FTH-2025-114", "0672000014", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(15, "Imane Filali",      "FTH-2025-115", "0672000015", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(16, "Karim Hajji",       "FTH-2025-116", "0672000016", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(17, "Ghita Labriki",     "FTH-2025-117", "0672000017", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(18, "Youssef Sebbar",    "FTH-2025-118", "0672000018", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(19, "Nawal Chraibi",     "FTH-2025-119", "0672000019", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(20, "Omar Boulaknadel",  "FTH-2025-120", "0672000020", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(21, "Halima El Ouali",   "FTH-2025-121", "0672000021", "Sidi Maarouf", "actif"),
                new BoiteClient.ClientPort(22, "Driss Tahiri",      "FTH-2025-122", "0672000022", "Sidi Maarouf", "actif")
            ));
            boiteClientRepository.save(b18);

            BoiteClient b19 = BoiteClient.builder()
                    .type("16FO").splitterId(sp17.getId())
                    .capacite(16).portsUtilises(0)
                    .latitude(33.5315).longitude(-7.6515)
                    .statut("actif").build();
            boiteClientRepository.save(b19);

            // sp18 (Bouskoura 1:16) → 2 boîtes
            BoiteClient b20 = BoiteClient.builder()
                    .type("24FO").splitterId(sp18.getId())
                    .capacite(24).portsUtilises(9)
                    .latitude(33.4975).longitude(-7.6650)
                    .statut("actif").build();
            b20.setClients(List.of(
                new BoiteClient.ClientPort(1, "Fatima Ezzahra Idrissi", "FTH-2025-123", "0673000001", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(2, "Hassan Bakkali",         "FTH-2025-124", "0673000002", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(3, "Amal Bensouda",          "FTH-2025-125", "0673000003", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(4, "Mustapha El Harti",      "FTH-2025-126", "0673000004", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(5, "Siham Belfkih",          "FTH-2025-127", "0673000005", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(6, "Anas Oubouhou",          "FTH-2025-128", "0673000006", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(7, "Kenza Bouazzaoui",       "FTH-2025-129", "0673000007", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(8, "Youssef Naciri",         "FTH-2025-130", "0673000008", "Bouskoura", "actif"),
                new BoiteClient.ClientPort(9, "Rajaa Tlemcani",         "FTH-2025-131", "0673000009", "Bouskoura", "actif")
            ));
            boiteClientRepository.save(b20);

            BoiteClient b21 = BoiteClient.builder()
                    .type("16FO").splitterId(sp18.getId())
                    .capacite(16).portsUtilises(0)
                    .latitude(33.4955).longitude(-7.6620)
                    .statut("actif").build();
            boiteClientRepository.save(b21);

            log.info("📦 21 Boîtes clients créées (130+ clients)");

            // ══════════════════════════════════════════
            // 7. CHEMINS FIBRE — 20 chemins
            // ══════════════════════════════════════════

            // DC1 → OLT1, OLT2, OLT3
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc1.getId(), dc1.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt1.getId(), olt1.getNom()))
                    .longueur("3.2km").typeFibre("SMF G.652D").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5928, -7.6192),
                        new CheminFibre.Coordonnee(2, 33.5875, -7.6270),
                        new CheminFibre.Coordonnee(3, 33.5820, -7.6350)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc1.getId(), dc1.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt2.getId(), olt2.getNom()))
                    .longueur("2.8km").typeFibre("SMF G.652D").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5928, -7.6192),
                        new CheminFibre.Coordonnee(2, 33.5955, -7.6290),
                        new CheminFibre.Coordonnee(3, 33.5980, -7.6380)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc1.getId(), dc1.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt3.getId(), olt3.getNom()))
                    .longueur("4.1km").typeFibre("SMF G.652D").statut("maintenance")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5928, -7.6192),
                        new CheminFibre.Coordonnee(2, 33.5914, -7.6350),
                        new CheminFibre.Coordonnee(3, 33.5900, -7.6500)
                    )).build());

            // DC2 → OLT4, OLT5, OLT6
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc2.getId(), dc2.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt4.getId(), olt4.getNom()))
                    .longueur("1.5km").typeFibre("SMF G.652D").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6078, -7.5156),
                        new CheminFibre.Coordonnee(2, 33.6114, -7.5178),
                        new CheminFibre.Coordonnee(3, 33.6150, -7.5200)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc2.getId(), dc2.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt5.getId(), olt5.getNom()))
                    .longueur("3.8km").typeFibre("SMF G.652D").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6078, -7.5156),
                        new CheminFibre.Coordonnee(2, 33.6014, -7.5278),
                        new CheminFibre.Coordonnee(3, 33.5950, -7.5400)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc2.getId(), dc2.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt6.getId(), olt6.getNom()))
                    .longueur("2.2km").typeFibre("SMF G.652D").statut("coupe")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6078, -7.5156),
                        new CheminFibre.Coordonnee(2, 33.6164, -7.5103),
                        new CheminFibre.Coordonnee(3, 33.6250, -7.5050)
                    )).build());

            // DC3 → OLT7, OLT8
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc3.getId(), dc3.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt7.getId(), olt7.getNom()))
                    .longueur("1.1km").typeFibre("SMF G.652D").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5274, -7.6431),
                        new CheminFibre.Coordonnee(2, 33.5312, -7.6491),
                        new CheminFibre.Coordonnee(3, 33.5350, -7.6550)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("datacenter", dc3.getId(), dc3.getNom()))
                    .destination(new CheminFibre.NoeudRef("repartiteur", olt8.getId(), olt8.getNom()))
                    .longueur("3.5km").typeFibre("SMF G.652D").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5274, -7.6431),
                        new CheminFibre.Coordonnee(2, 33.5126, -7.6526),
                        new CheminFibre.Coordonnee(3, 33.4980, -7.6620)
                    )).build());

            // OLT → Splitters
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt1.getId(), olt1.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp1.getId(), "SP-"+sp1.getRatio()))
                    .longueur("0.4km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5820, -7.6350),
                        new CheminFibre.Coordonnee(2, 33.5800, -7.6390),
                        new CheminFibre.Coordonnee(3, 33.5780, -7.6400)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt1.getId(), olt1.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp2.getId(), "SP-"+sp2.getRatio()))
                    .longueur("0.3km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5820, -7.6350),
                        new CheminFibre.Coordonnee(2, 33.5835, -7.6385),
                        new CheminFibre.Coordonnee(3, 33.5850, -7.6420)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt1.getId(), olt1.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp3.getId(), "SP-"+sp3.getRatio()))
                    .longueur("0.6km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5820, -7.6350),
                        new CheminFibre.Coordonnee(2, 33.5790, -7.6325),
                        new CheminFibre.Coordonnee(3, 33.5760, -7.6300)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt2.getId(), olt2.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp4.getId(), "SP-"+sp4.getRatio()))
                    .longueur("0.5km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5980, -7.6380),
                        new CheminFibre.Coordonnee(2, 33.5985, -7.6400),
                        new CheminFibre.Coordonnee(3, 33.5990, -7.6420)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt2.getId(), olt2.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp5.getId(), "SP-"+sp5.getRatio()))
                    .longueur("0.4km").typeFibre("SMF G.657A2").statut("maintenance")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5980, -7.6380),
                        new CheminFibre.Coordonnee(2, 33.5995, -7.6365),
                        new CheminFibre.Coordonnee(3, 33.6010, -7.6350)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt4.getId(), olt4.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp6.getId(), "SP-"+sp6.getRatio()))
                    .longueur("0.5km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6150, -7.5200),
                        new CheminFibre.Coordonnee(2, 33.6165, -7.5225),
                        new CheminFibre.Coordonnee(3, 33.6180, -7.5250)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt5.getId(), olt5.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp8.getId(), "SP-"+sp8.getRatio()))
                    .longueur("0.6km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5950, -7.5400),
                        new CheminFibre.Coordonnee(2, 33.5935, -7.5425),
                        new CheminFibre.Coordonnee(3, 33.5920, -7.5450)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt7.getId(), olt7.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp10.getId(), "SP-"+sp10.getRatio()))
                    .longueur("0.4km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5350, -7.6550),
                        new CheminFibre.Coordonnee(2, 33.5365, -7.6575),
                        new CheminFibre.Coordonnee(3, 33.5380, -7.6600)
                    )).build());

            // Splitter → Boîtes (quelques chemins de distribution)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp1.getId(), "SP-"+sp1.getRatio()))
                    .destination(new CheminFibre.NoeudRef("boite", b1.getId(), "Boîte Maarif-01"))
                    .longueur("0.12km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5780, -7.6400),
                        new CheminFibre.Coordonnee(2, 33.5773, -7.6415),
                        new CheminFibre.Coordonnee(3, 33.5765, -7.6430)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp8.getId(), "SP-"+sp8.getRatio()))
                    .destination(new CheminFibre.NoeudRef("boite", b10.getId(), "Boîte HayMoh-01"))
                    .longueur("0.18km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5920, -7.5450),
                        new CheminFibre.Coordonnee(2, 33.5925, -7.5465),
                        new CheminFibre.Coordonnee(3, 33.5930, -7.5480)
                    )).build());

            // OLT3 → Splitters sp13, sp14 (Racine)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt3.getId(), olt3.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp13.getId(), "SP-"+sp13.getRatio()))
                    .longueur("0.4km").typeFibre("SMF G.657A2").statut("maintenance")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5900, -7.6500),
                        new CheminFibre.Coordonnee(2, 33.5892, -7.6505),
                        new CheminFibre.Coordonnee(3, 33.5885, -7.6510)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt3.getId(), olt3.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp14.getId(), "SP-"+sp14.getRatio()))
                    .longueur("0.3km").typeFibre("SMF G.657A2").statut("maintenance")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5900, -7.6500),
                        new CheminFibre.Coordonnee(2, 33.5907, -7.6495),
                        new CheminFibre.Coordonnee(3, 33.5915, -7.6490)
                    )).build());

            // OLT6 → Splitters sp15, sp16 (Bernoussi)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt6.getId(), olt6.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp15.getId(), "SP-"+sp15.getRatio()))
                    .longueur("0.3km").typeFibre("SMF G.657A2").statut("coupe")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6250, -7.5050),
                        new CheminFibre.Coordonnee(2, 33.6245, -7.5055),
                        new CheminFibre.Coordonnee(3, 33.6240, -7.5060)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt6.getId(), olt6.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp16.getId(), "SP-"+sp16.getRatio()))
                    .longueur("0.2km").typeFibre("SMF G.657A2").statut("coupe")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6250, -7.5050),
                        new CheminFibre.Coordonnee(2, 33.6255, -7.5045),
                        new CheminFibre.Coordonnee(3, 33.6260, -7.5040)
                    )).build());

            // OLT7 → Splitter sp17 (Sidi Maarouf haute densité)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt7.getId(), olt7.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp17.getId(), "SP-"+sp17.getRatio()))
                    .longueur("0.3km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5350, -7.6550),
                        new CheminFibre.Coordonnee(2, 33.5340, -7.6540),
                        new CheminFibre.Coordonnee(3, 33.5330, -7.6530)
                    )).build());

            // OLT8 → Splitter sp18 (Bouskoura secondaire)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt8.getId(), olt8.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp18.getId(), "SP-"+sp18.getRatio()))
                    .longueur("0.3km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.4980, -7.6620),
                        new CheminFibre.Coordonnee(2, 33.4975, -7.6628),
                        new CheminFibre.Coordonnee(3, 33.4970, -7.6635)
                    )).build());

            // Splitters nouveaux → Boîtes
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp13.getId(), "SP-"+sp13.getRatio()))
                    .destination(new CheminFibre.NoeudRef("boite", b16.getId(), "Boîte Racine-01"))
                    .longueur("0.08km").typeFibre("SMF G.657A2").statut("maintenance")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5885, -7.6510),
                        new CheminFibre.Coordonnee(2, 33.5882, -7.6517),
                        new CheminFibre.Coordonnee(3, 33.5880, -7.6525)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp14.getId(), "SP-"+sp14.getRatio()))
                    .destination(new CheminFibre.NoeudRef("boite", b17.getId(), "Boîte Racine-02"))
                    .longueur("0.10km").typeFibre("SMF G.657A2").statut("maintenance")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5915, -7.6490),
                        new CheminFibre.Coordonnee(2, 33.5917, -7.6495),
                        new CheminFibre.Coordonnee(3, 33.5920, -7.6500)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp17.getId(), "SP-"+sp17.getRatio()))
                    .destination(new CheminFibre.NoeudRef("boite", b18.getId(), "Boîte SidMaarif-03"))
                    .longueur("0.10km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5330, -7.6530),
                        new CheminFibre.Coordonnee(2, 33.5327, -7.6537),
                        new CheminFibre.Coordonnee(3, 33.5325, -7.6545)
                    )).build());

            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("splitter", sp18.getId(), "SP-"+sp18.getRatio()))
                    .destination(new CheminFibre.NoeudRef("boite", b20.getId(), "Boîte Bouskoura-03"))
                    .longueur("0.15km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.4970, -7.6635),
                        new CheminFibre.Coordonnee(2, 33.4972, -7.6643),
                        new CheminFibre.Coordonnee(3, 33.4975, -7.6650)
                    )).build());

            // sp7 (Ain Sebaâ 1:8) → boîte directe
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt4.getId(), olt4.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp7.getId(), "SP-"+sp7.getRatio()))
                    .longueur("0.4km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.6150, -7.5200),
                        new CheminFibre.Coordonnee(2, 33.6135, -7.5190),
                        new CheminFibre.Coordonnee(3, 33.6120, -7.5180)
                    )).build());

            // sp9 (Hay Mohammadi 1:16) → OLT5 (chemin manquant)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt5.getId(), olt5.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp9.getId(), "SP-"+sp9.getRatio()))
                    .longueur("0.5km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5950, -7.5400),
                        new CheminFibre.Coordonnee(2, 33.5965, -7.5390),
                        new CheminFibre.Coordonnee(3, 33.5980, -7.5380)
                    )).build());

            // sp11 (Sidi Maarouf 1:32) → OLT7 (chemin manquant)
            cheminFibreRepository.save(CheminFibre.builder()
                    .source(new CheminFibre.NoeudRef("repartiteur", olt7.getId(), olt7.getNom()))
                    .destination(new CheminFibre.NoeudRef("splitter", sp11.getId(), "SP-"+sp11.getRatio()))
                    .longueur("0.6km").typeFibre("SMF G.657A2").statut("actif")
                    .coordonnees(List.of(
                        new CheminFibre.Coordonnee(1, 33.5350, -7.6550),
                        new CheminFibre.Coordonnee(2, 33.5335, -7.6525),
                        new CheminFibre.Coordonnee(3, 33.5320, -7.6500)
                    )).build());

            log.info("🔗 32 Chemins fibre créés");

            // ══════════════════════════════════════════
            // RÉSUMÉ FINAL
            // ══════════════════════════════════════════
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.info("✅ Initialisation terminée — Réseau FTTH Casablanca");
            log.info("   👤 Users        : {}", userRepository.count());
            log.info("   🏢 Datacenters  : {}", datacenterRepository.count());
            log.info("   📡 Répartiteurs : {}", repartiteurRepository.count());
            log.info("   ⚙️  Équipements  : {}", equipementRepository.count());
            log.info("   🔀 Splitters    : {}", splitterRepository.count());
            log.info("   📦 Boîtes       : {}", boiteClientRepository.count());
            log.info("   🔗 Chemins      : {}", cheminFibreRepository.count());
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            log.info("🔑 admin/admin123 — technicien1/tech123 — technicien2/tech123");
            log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        };
    }
}