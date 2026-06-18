package ma.telecom.fibre.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.EquipementRequest;
import ma.telecom.fibre.dtos.StatutRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.models.Equipement;
import ma.telecom.fibre.services.EquipementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final EquipementService service;

    @GetMapping
    public ResponseEntity<List<Equipement>> getAll(
            @RequestParam(required = false) String repartiteurId,
            @RequestParam(required = false) String type) {
        if (repartiteurId != null) return ResponseEntity.ok(service.findByRepartiteur(repartiteurId));
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipement> create(@Valid @RequestBody EquipementRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    // Technicien peut changer le statut (RG08)
    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIEN')")
    public ResponseEntity<Equipement> updateStatut(@PathVariable String id,
                                                   @Valid @RequestBody StatutRequest req) {
        return ResponseEntity.ok(service.updateStatut(id, req.getStatut()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Équipement supprimé"));
    }
}