package ma.telecom.fibre.services;

import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.SplitterRequest;
import ma.telecom.fibre.exceptions.BusinessRuleException;
import ma.telecom.fibre.exceptions.ResourceNotFoundException;
import ma.telecom.fibre.models.Splitter;
import ma.telecom.fibre.repositories.RepartiteurRepository;
import ma.telecom.fibre.repositories.SplitterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SplitterService {

    private final SplitterRepository repository;
    private final RepartiteurRepository repartiteurRepository;

    public List<Splitter> findAll() {
        return repository.findAll();
    }

    public List<Splitter> findByRepartiteur(String repartiteurId) {
        return repository.findByRepartiteurId(repartiteurId);
    }

    public Splitter findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Splitter", id));
    }

    public Splitter create(SplitterRequest req) {
        // RG02 : le répartiteur doit exister
        if (!repartiteurRepository.existsById(req.getRepartiteurId()))
            throw new BusinessRuleException("Répartiteur introuvable : " + req.getRepartiteurId());

        // Calcule nbSorties selon le ratio
        int sorties = switch (req.getRatio()) {
            case "1:8"  -> 8;
            case "1:16" -> 16;
            case "1:32" -> 32;
            default -> throw new BusinessRuleException("Ratio invalide");
        };

        Splitter splitter = Splitter.builder()
                .ratio(req.getRatio())
                .repartiteurId(req.getRepartiteurId())
                .nbSorties(sorties)
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .statut(req.getStatut() != null ? req.getStatut() : "actif")
                .build();

        return repository.save(splitter);
    }

    public Splitter updateStatut(String id, String statut) {
        Splitter s = findById(id);
        s.setStatut(statut);
        return repository.save(s);
    }

    public void delete(String id) {
        repository.delete(findById(id));
    }
}