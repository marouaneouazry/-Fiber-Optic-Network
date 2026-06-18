package ma.telecom.fibre.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.RepartiteurRequest;
import ma.telecom.fibre.dtos.StatutRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.models.Repartiteur;
import ma.telecom.fibre.services.RepartiteurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repartiteurs")
@RequiredArgsConstructor
public class RepartiteurController {

    private final RepartiteurService service;

    @GetMapping
    public ResponseEntity<List<Repartiteur>> getAll(
            @RequestParam(required = false) String datacenterId) {
        List<Repartiteur> result = datacenterId != null
                ? service.findByDatacenter(datacenterId)
                : service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repartiteur> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Repartiteur> create(@Valid @RequestBody RepartiteurRequest req) {
        // Mappe le DTO vers le modèle
        Repartiteur r = new Repartiteur();
        r.setNom(req.getNom());
        r.setDatacenterId(req.getDatacenterId());
        r.setNbPorts(req.getNbPorts());
        r.setLatitude(req.getLatitude());
        r.setLongitude(req.getLongitude());
        r.setStatut(req.getStatut() != null ? req.getStatut() : "actif");
        return ResponseEntity.status(201).body(service.create(r));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIEN')")
    public ResponseEntity<Repartiteur> updateStatut(@PathVariable String id,
                                                    @Valid @RequestBody StatutRequest req) {
        return ResponseEntity.ok(service.updateStatut(id, req.getStatut()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Répartiteur supprimé"));
    }
}