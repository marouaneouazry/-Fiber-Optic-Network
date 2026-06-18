package ma.telecom.fibre.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.BoiteClientRequest;
import ma.telecom.fibre.dtos.StatutRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.models.BoiteClient;
import ma.telecom.fibre.services.BoiteClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boites-clients")
@RequiredArgsConstructor
public class BoiteClientController {

    private final BoiteClientService service;

    @GetMapping
    public ResponseEntity<List<BoiteClient>> getAll(
            @RequestParam(required = false) String splitterId) {
        List<BoiteClient> result = splitterId != null
                ? service.findBySplitter(splitterId)
                : service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoiteClient> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BoiteClient> create(@Valid @RequestBody BoiteClientRequest req) {
        BoiteClient boite = new BoiteClient();
        boite.setType(req.getType());
        boite.setSplitterId(req.getSplitterId());
        boite.setLatitude(req.getLatitude());
        boite.setLongitude(req.getLongitude());
        boite.setStatut(req.getStatut() != null ? req.getStatut() : "actif");
        return ResponseEntity.status(201).body(service.create(boite));
    }

    // Ajouter un client sur un port de la boîte
    @PostMapping("/{id}/clients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BoiteClient> ajouterClient(
            @PathVariable String id,
            @RequestBody BoiteClient.ClientPort client) {
        return ResponseEntity.ok(service.ajouterClient(id, client));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIEN')")
    public ResponseEntity<BoiteClient> updateStatut(@PathVariable String id,
                                                    @Valid @RequestBody StatutRequest req) {
        return ResponseEntity.ok(service.updateStatut(id, req.getStatut()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Boîte client supprimée"));
    }
}