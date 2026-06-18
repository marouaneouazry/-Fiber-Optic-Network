package ma.telecom.fibre.repositories;

import ma.telecom.fibre.models.CheminFibre;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CheminFibreRepository extends MongoRepository<CheminFibre, String> {
    List<CheminFibre> findByStatut(String statut);
    List<CheminFibre> findBySourceId(String sourceId);
}