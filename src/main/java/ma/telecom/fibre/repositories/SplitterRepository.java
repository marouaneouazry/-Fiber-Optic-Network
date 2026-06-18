package ma.telecom.fibre.repositories;

import ma.telecom.fibre.models.Splitter;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SplitterRepository extends MongoRepository<Splitter, String> {
    List<Splitter> findByRepartiteurId(String repartiteurId);
    List<Splitter> findByStatut(String statut);
}