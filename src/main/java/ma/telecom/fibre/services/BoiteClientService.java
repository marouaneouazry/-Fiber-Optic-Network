package ma.telecom.fibre.services;


import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.exceptions.BusinessRuleException;
import ma.telecom.fibre.exceptions.ResourceNotFoundException;
import ma.telecom.fibre.models.BoiteClient;
import ma.telecom.fibre.repositories.BoiteClientRepository;
import ma.telecom.fibre.repositories.SplitterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoiteClientService {

    private final BoiteClientRepository repository;
    private final SplitterRepository splitterRepository;

    public List<BoiteClient> findAll() { return repository.findAll(); }

    public BoiteClient findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boîte client", id));
    }

    public BoiteClient create(BoiteClient req) {
        // RG03 : le splitter doit exister
        if (!splitterRepository.existsById(req.getSplitterId()))
            throw new BusinessRuleException("Splitter introuvable : " + req.getSplitterId());

        // Définit la capacité selon le type
        if ("16FO".equals(req.getType())) req.setCapacite(16);
        else if ("24FO".equals(req.getType())) req.setCapacite(24);

        return repository.save(req);
    }

    public BoiteClient ajouterClient(String id, BoiteClient.ClientPort client) {
        BoiteClient boite = findById(id);

        // RG04 : vérifier la capacité
        if (boite.getPortsUtilises() >= boite.getCapacite())
            throw new BusinessRuleException(
                    "Capacité maximale atteinte (" + boite.getCapacite() + " ports)");

        // Attribuer automatiquement le premier port libre
        int freePort = -1;
        for (int i = 1; i <= boite.getCapacite(); i++) {
            final int currentPort = i;
            if (boite.getClients().stream().noneMatch(c -> c.getPort() == currentPort)) {
                freePort = currentPort;
                break;
            }
        }
        if (freePort == -1) {
            throw new BusinessRuleException("Erreur lors de l'attribution du port.");
        }
        
        client.setPort(freePort);
        boite.getClients().add(client);
        boite.setPortsUtilises(boite.getPortsUtilises() + 1);
        return repository.save(boite);
    }

    public BoiteClient updateClient(String id, int port, BoiteClient.ClientPort updatedClient) {
        BoiteClient boite = findById(id);
        BoiteClient.ClientPort client = boite.getClients().stream()
                .filter(c -> c.getPort() == port)
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException("Client non trouvé sur le port " + port));

        client.setNom(updatedClient.getNom());
        client.setContrat(updatedClient.getContrat());
        client.setTelephone(updatedClient.getTelephone());
        client.setAdresse(updatedClient.getAdresse());
        client.setStatut(updatedClient.getStatut());

        return repository.save(boite);
    }

    public BoiteClient deleteClient(String id, int port) {
        BoiteClient boite = findById(id);
        boolean removed = boite.getClients().removeIf(c -> c.getPort() == port);
        if (removed) {
            boite.setPortsUtilises(boite.getPortsUtilises() - 1);
            return repository.save(boite);
        } else {
            throw new BusinessRuleException("Client non trouvé sur le port " + port);
        }
    }

    public void delete(String id) {
        repository.delete(findById(id));
    }
    // Ajouter dans BoiteClientService.java
    public List<BoiteClient> findBySplitter(String splitterId) {
        return repository.findBySplitterId(splitterId);
    }

    public BoiteClient updateStatut(String id, String statut) {
        BoiteClient b = findById(id);
        b.setStatut(statut);
        return repository.save(b);
    }
}
