package ma.telecom.fibre.services;


import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.exceptions.BusinessRuleException;
import ma.telecom.fibre.exceptions.ResourceNotFoundException;
import ma.telecom.fibre.models.Repartiteur;
import ma.telecom.fibre.repositories.DatacenterRepository;
import ma.telecom.fibre.repositories.RepartiteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepartiteurService {

    private final RepartiteurRepository repository;
    private final DatacenterRepository datacenterRepository;

    public List<Repartiteur> findAll() { return repository.findAll(); }

    public List<Repartiteur> findByDatacenter(String datacenterId) {
        return repository.findByDatacenterId(datacenterId);
    }

    public Repartiteur findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Répartiteur", id));
    }

    public Repartiteur create(Repartiteur req) {
        // RG01 : vérifier que le Datacenter existe
        if (!datacenterRepository.existsById(req.getDatacenterId()))
            throw new BusinessRuleException("Datacenter introuvable : " + req.getDatacenterId());
        return repository.save(req);
    }

    public Repartiteur updateStatut(String id, String statut) {
        Repartiteur r = findById(id);
        r.setStatut(statut);
        return repository.save(r);
    }

    public void delete(String id) {
        repository.delete(findById(id));
    }
}