package ma.telecom.fibre.services;

import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.EquipementRequest;
import ma.telecom.fibre.exceptions.ResourceNotFoundException;
import ma.telecom.fibre.models.Equipement;
import ma.telecom.fibre.repositories.EquipementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementService {

    private final EquipementRepository repository;

    public List<Equipement> findAll() { return repository.findAll(); }

    public List<Equipement> findByRepartiteur(String repartiteurId) {
        return repository.findByRepartiteurId(repartiteurId);
    }

    public Equipement findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    public Equipement create(EquipementRequest req) {
        Equipement eq = Equipement.builder()
                .type(req.getType())
                .ip(req.getIp())
                .marque(req.getMarque())
                .modele(req.getModele())
                .emplacement(req.getEmplacement())
                .repartiteurId(req.getRepartiteurId())
                .statut(req.getStatut() != null ? req.getStatut() : "actif")
                .build();
        return repository.save(eq);
    }

    public Equipement updateStatut(String id, String statut) {
        Equipement eq = findById(id);
        eq.setStatut(statut);
        return repository.save(eq);
    }

    public void delete(String id) {
        repository.delete(findById(id));
    }
}