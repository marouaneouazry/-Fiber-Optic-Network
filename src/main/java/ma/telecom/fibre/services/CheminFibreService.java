package ma.telecom.fibre.services;

import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.CheminFibreRequest;
import ma.telecom.fibre.exceptions.ResourceNotFoundException;
import ma.telecom.fibre.models.CheminFibre;
import ma.telecom.fibre.repositories.CheminFibreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheminFibreService {

    private final CheminFibreRepository repository;

    public List<CheminFibre> findAll() {
        return repository.findAll();
    }

    public CheminFibre findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chemin fibre", id));
    }

    public CheminFibre create(CheminFibreRequest req) {
        java.util.List<String> allowedFiberTypes = java.util.List.of("SMF G.652D", "SMF G.657A2", "SMF G.657B3");
        if (req.getTypeFibre() != null && !allowedFiberTypes.contains(req.getTypeFibre())) {
            throw new ma.telecom.fibre.exceptions.BusinessRuleException("Type de fibre non autorisé : " + req.getTypeFibre());
        }

        CheminFibre chemin = CheminFibre.builder()
                .source(req.getSource())
                .destination(req.getDestination())
                .longueur(req.getLongueur())
                .typeFibre(req.getTypeFibre())
                .statut(req.getStatut() != null ? req.getStatut() : "actif")
                .coordonnees(req.getCoordonnees())
                .build();

        return repository.save(chemin);
    }

    public CheminFibre updateStatut(String id, String statut) {
        CheminFibre c = findById(id);
        c.setStatut(statut);
        return repository.save(c);
    }

    public void delete(String id) {
        repository.delete(findById(id));
    }
}