package ma.telecom.fibre.services;


import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.DatacenterRequest;
import ma.telecom.fibre.exceptions.ResourceNotFoundException;
import ma.telecom.fibre.models.Datacenter;
import ma.telecom.fibre.repositories.DatacenterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatacenterService {

    private final DatacenterRepository repository;

    public List<Datacenter> findAll() {
        return repository.findAll();
    }

    public List<Datacenter> findByStatut(String statut) {
        return repository.findByStatut(statut);
    }

    public Datacenter findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Datacenter", id));
    }

    public Datacenter create(DatacenterRequest req) {
        Datacenter dc = Datacenter.builder()
                .nom(req.getNom())
                .localisation(req.getLocalisation())
                .capacite(req.getCapacite())
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .statut(req.getStatut() != null ? req.getStatut() : "actif")
                .build();
        return repository.save(dc);
    }

    public Datacenter update(String id, DatacenterRequest req) {
        Datacenter dc = findById(id);
        dc.setNom(req.getNom());
        dc.setLocalisation(req.getLocalisation());
        dc.setCapacite(req.getCapacite());
        dc.setLatitude(req.getLatitude());
        dc.setLongitude(req.getLongitude());
        if (req.getStatut() != null) dc.setStatut(req.getStatut());
        dc.setUpdatedAt(LocalDateTime.now());
        return repository.save(dc);
    }

    public void delete(String id) {
        Datacenter dc = findById(id);
        repository.delete(dc);
    }
}