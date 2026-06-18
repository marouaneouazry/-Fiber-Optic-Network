package ma.telecom.fibre.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.CheminFibreRequest;
import ma.telecom.fibre.dtos.StatutRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.models.CheminFibre;
import ma.telecom.fibre.services.CheminFibreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chemins")
@RequiredArgsConstructor
public class CheminFibreController {

    private final CheminFibreService service;

    @GetMapping
    public ResponseEntity<List<CheminFibre>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheminFibre> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CheminFibre> create(@Valid @RequestBody CheminFibreRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN','TECHNICIEN')")
    public ResponseEntity<CheminFibre> updateStatut(@PathVariable String id,
                                                    @Valid @RequestBody StatutRequest req) {
        return ResponseEntity.ok(service.updateStatut(id, req.getStatut()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Chemin supprimé"));
    }
}