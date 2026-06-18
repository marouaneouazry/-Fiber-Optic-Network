package ma.telecom.fibre.repositories;

import ma.telecom.fibre.models.Datacenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DatacenterRepository extends MongoRepository<Datacenter, String> {
    List<Datacenter> findByStatut(String statut);
    List<Datacenter> findByNomContainingIgnoreCase(String nom);
}