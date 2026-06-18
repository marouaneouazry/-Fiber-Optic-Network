package ma.telecom.fibre.repositories;

import ma.telecom.fibre.models.Equipement;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EquipementRepository extends MongoRepository<Equipement, String> {
    List<Equipement> findByRepartiteurId(String repartiteurId);
    List<Equipement> findByStatut(String statut);
    List<Equipement> findByType(String type);
}
