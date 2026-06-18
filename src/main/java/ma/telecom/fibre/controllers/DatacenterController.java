package ma.telecom.fibre.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.DatacenterRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.models.Datacenter;
import ma.telecom.fibre.services.DatacenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datacenters")
@RequiredArgsConstructor
public class DatacenterController {

    private final DatacenterService service;

    @GetMapping
    public ResponseEntity<List<Datacenter>> getAll(
            @RequestParam(required = false) String statut) {
        List<Datacenter> result = statut != null
                ? service.findByStatut(statut)
                : service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Datacenter> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Datacenter> create(@Valid @RequestBody DatacenterRequest req) {
        return ResponseEntity.status(201).body(service.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Datacenter> update(@PathVariable String id,
                                             @Valid @RequestBody DatacenterRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Datacenter supprimé"));
    }
}