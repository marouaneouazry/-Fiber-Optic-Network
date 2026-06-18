package ma.telecom.fibre.repositories;

import ma.telecom.fibre.models.Repartiteur;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RepartiteurRepository extends MongoRepository<Repartiteur, String> {
    List<Repartiteur> findByDatacenterId(String datacenterId);
    List<Repartiteur> findByStatut(String statut);
}