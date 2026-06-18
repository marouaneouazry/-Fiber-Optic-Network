package ma.telecom.fibre.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.SplitterRequest;
import ma.telecom.fibre.dtos.StatutRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.models.Splitter;
import ma.telecom.fibre.services.SplitterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/splitters")
@RequiredArgsConstructor
public class SplitterController {

    private final SplitterService service;

    @GetMapping
    public ResponseEntity<List<Splitter>> getAll(
            @RequestParam(required = false) String repartiteurId) {
        List<Splitter> result = repartiteurId != null
                ? service.findByRepartiteur(repartiteurId)
                : service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Splitter> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Splitter> create(@Valid @RequestBody SplitterRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIEN')")
    public ResponseEntity<Splitter> updateStatut(@PathVariable String id,
                                                 @Valid @RequestBody StatutRequest req) {
        return ResponseEntity.ok(service.updateStatut(id, req.getStatut()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Splitter supprimé"));
    }
}