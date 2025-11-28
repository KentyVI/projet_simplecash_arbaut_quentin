package com.example.simplecash.service;

import com.example.simplecash.entity.*;
import com.example.simplecash.repository.ClientRepository;
import com.example.simplecash.repository.CompteCourantRepository;
import com.example.simplecash.repository.CompteEpargneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final CompteCourantRepository compteCourantRepository;
    private final CompteEpargneRepository compteEpargneRepository;

    public ClientService(ClientRepository clientRepository, CompteCourantRepository compteCourantRepository, CompteEpargneRepository compteEpargneRepository) {
        this.clientRepository = clientRepository;
        this.compteCourantRepository = compteCourantRepository;
        this.compteEpargneRepository = compteEpargneRepository;
    }

    public List<Client> findAll(){ return clientRepository.findAll(); }
    public Client get(Long id){ return clientRepository.findById(id).orElseThrow(); }

    @Transactional
    public Client ajouterCompteCourant(Long clientId, String numero, double soldeInitial, Double decouvert){
        Client c = get(clientId);
        CompteCourant cc = new CompteCourant();
        cc.setNumeroCompte(numero);
        cc.setSolde(soldeInitial);
        cc.setDateOuverture(LocalDate.now());
        if(decouvert!=null) cc.setDecouvertAutorise(decouvert);
        compteCourantRepository.save(cc);
        c.setCompteCourant(cc);
        return c;
    }

    @Transactional
    public Client ajouterCompteEpargne(Long clientId, String numero, double soldeInitial, Double taux){
        Client c = get(clientId);
        CompteEpargne ce = new CompteEpargne();
        ce.setNumeroCompte(numero);
        ce.setSolde(soldeInitial);
        ce.setDateOuverture(LocalDate.now());
        if(taux!=null) ce.setTauxRemuneration(taux);
        compteEpargneRepository.save(ce);
        c.setCompteEpargne(ce);
        return c;
    }
}

