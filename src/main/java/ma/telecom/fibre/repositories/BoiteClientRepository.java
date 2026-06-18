package ma.telecom.fibre.repositories;

import ma.telecom.fibre.models.BoiteClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BoiteClientRepository extends MongoRepository<BoiteClient, String> {
    List<BoiteClient> findBySplitterId(String splitterId);
    List<BoiteClient> findByStatut(String statut);
}