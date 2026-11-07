package ma.telecom.fibre.controllers;


import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.models.*;
import ma.telecom.fibre.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reseau")
@RequiredArgsConstructor
public class ReseauController {

    private final DatacenterRepository datacenterRepo;
    private final RepartiteurRepository repartiteurRepo;
    private final SplitterRepository splitterRepo;
    private final BoiteClientRepository boiteRepo;
    private final CheminFibreRepository cheminRepo;
    private final EquipementRepository equipementRepo;

    // Retourne tous les nœuds + chemins pour la carte Leaflet
    @GetMapping("/carte")
    public ResponseEntity<Map<String, Object>> getCarte() {
        List<Map<String, Object>> noeuds = new ArrayList<>();

        // Datacenters
        datacenterRepo.findAll().forEach(dc -> noeuds.add(Map.of(
                "id", dc.getId(), "type", "datacenter",
                "nom", dc.getNom(), "statut", dc.getStatut(),
                "latitude", dc.getLatitude(), "longitude", dc.getLongitude()
        )));

        // Répartiteurs
        repartiteurRepo.findAll().forEach(r -> noeuds.add(Map.of(
                "id", r.getId(), "type", "repartiteur",
                "nom", r.getNom(), "statut", r.getStatut(),
                "latitude", r.getLatitude(), "longitude", r.getLongitude()
        )));

        // Splitters
        splitterRepo.findAll().forEach(s -> noeuds.add(Map.of(
                "id", s.getId(), "type", "splitter",
                "nom", "Splitter " + s.getRatio(), "statut", s.getStatut(),
                "latitude", s.getLatitude(), "longitude", s.getLongitude()
        )));

        // Boîtes clients
        boiteRepo.findAll().forEach(b -> noeuds.add(Map.of(
                "id", b.getId(), "type", "boite",
                "nom", "Boîte " + b.getType(), "statut", b.getStatut(),
                "latitude", b.getLatitude(), "longitude", b.getLongitude()
        )));

        return ResponseEntity.ok(Map.of(
                "noeuds", noeuds,
                "chemins", cheminRepo.findAll()
        ));
    }

    // Statistiques globales pour le dashboard
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(Map.of(
                "datacenters", datacenterRepo.count(),
                "repartiteurs", repartiteurRepo.count(),
                "splitters", splitterRepo.count(),
                "boites", boiteRepo.count(),
                "chemins", cheminRepo.count(),
                "equipementsActifs", equipementRepo.count()
        ));
    }
}