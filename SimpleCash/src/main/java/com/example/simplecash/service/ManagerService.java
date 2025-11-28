package com.example.simplecash.service;

import com.example.simplecash.entity.*;
import com.example.simplecash.repository.ClientRepository;
import com.example.simplecash.repository.ConseillerRepository;
import com.example.simplecash.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ConseillerRepository conseillerRepository;
    private final ClientRepository clientRepository;

    public ManagerService(ManagerRepository managerRepository,
                          ConseillerRepository conseillerRepository,
                          ClientRepository clientRepository) {
        this.managerRepository = managerRepository;
        this.conseillerRepository = conseillerRepository;
        this.clientRepository = clientRepository;
    }

    public List<Manager> findAll(){ return managerRepository.findAll(); }
    public Manager get(Long id){ return managerRepository.findById(id).orElseThrow(); }
    public Manager create(Manager g){ return managerRepository.save(g); }
    public Manager update(Long id, Manager payload){
        Manager g = get(id);
        g.setNom(payload.getNom());
        g.setPrenom(payload.getPrenom());
        g.setEmail(payload.getEmail());
        g.setTelephone(payload.getTelephone());
        return managerRepository.save(g);
    }
    public void delete(Long id){ managerRepository.deleteById(id); }

    public Conseiller addConseiller(Long managerId, Conseiller conseiller){
        Manager g = get(managerId);
        conseiller.setManager(g);
        return conseillerRepository.save(conseiller);
    }

    // Audit global des comptes (Particuliers/Entreprises)
    public List<String> auditerComptes() {
        List<String> anomalies = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            if (client.getCompteCourant() != null) {
                double solde = client.getCompteCourant().getSolde() != null ? client.getCompteCourant().getSolde() : 0d;

                if (client.getTypeClient() == TypeClient.PARTICULIER && solde < -5000) {
                    anomalies.add("ALERTE [Particulier] - Client ID " + client.getId() + " (" + client.getNom() + ") : Solde " + solde + " < -5000");
                }

                if (client.getTypeClient() == TypeClient.ENTREPRISES && solde < -50000) {
                    anomalies.add("ALERTE [Entreprise] - Client ID " + client.getId() + " (" + client.getNom() + ") : Solde " + solde + " < -50000");
                }
            }
        }

        if (anomalies.isEmpty()) {
            anomalies.add("Audit terminé : Aucune anomalie détectée.");
        }
        return anomalies;
    }
}
