package com.example.simplecash.service;

import com.example.simplecash.dto.ClientCreateDTO;
import com.example.simplecash.dto.SimulationCreditDTO;
import com.example.simplecash.dto.VirementDTO;
import com.example.simplecash.entity.*;
import com.example.simplecash.repository.ClientRepository;
import com.example.simplecash.repository.ConseillerRepository;
import com.example.simplecash.repository.GerantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConseillerService {
    private final ConseillerRepository conseillerRepository;
    private final ClientRepository clientRepository;
    private final GerantRepository gerantRepository;

    public ConseillerService(ConseillerRepository conseillerRepository, ClientRepository clientRepository, GerantRepository gerantRepository) {
        this.conseillerRepository = conseillerRepository;
        this.clientRepository = clientRepository;
        this.gerantRepository = gerantRepository;
    }

    public List<Conseiller> findAll(){ return conseillerRepository.findAll(); }
    public Conseiller get(Long id){ return conseillerRepository.findById(id).orElseThrow(); }
    public Conseiller create(Conseiller c, Long gerantId){
        if(gerantId!=null){
            Gerant g = gerantRepository.findById(gerantId).orElseThrow();
            c.setGerant(g);
        }
        return conseillerRepository.save(c);
    }
    public Conseiller update(Long id, Conseiller payload){
        Conseiller c = get(id);
        c.setNom(payload.getNom());
        c.setPrenom(payload.getPrenom());
        c.setEmail(payload.getEmail());
        c.setTelephone(payload.getTelephone());
        return conseillerRepository.save(c);
    }
    public void delete(Long id){ conseillerRepository.deleteById(id); }

    public Client creerClient(Long conseillerId, ClientCreateDTO dto){
        Conseiller cons = get(conseillerId);
        if(!cons.peutAjouterClient()){
            throw new IllegalStateException("Ce conseiller a déjà 10 clients");
        }
        Client c = new Client();
        c.setNom(dto.getNom());
        c.setPrenom(dto.getPrenom());
        c.setAdresse(dto.getAdresse());
        c.setCodePostal(dto.getCodePostal());
        c.setVille(dto.getVille());
        c.setTelephone(dto.getTelephone());
        c.setTypeClient(dto.getTypeClient());
        c.setConseiller(cons);
        cons.getClients().add(c);
        return clientRepository.save(c);
    }

    public Client modifierClient(Long clientId, ClientCreateDTO dto){
        Client c = clientRepository.findById(clientId).orElseThrow();
        if(dto.getNom()!=null) c.setNom(dto.getNom());
        if(dto.getPrenom()!=null) c.setPrenom(dto.getPrenom());
        if(dto.getAdresse()!=null) c.setAdresse(dto.getAdresse());
        if(dto.getCodePostal()!=null) c.setCodePostal(dto.getCodePostal());
        if(dto.getVille()!=null) c.setVille(dto.getVille());
        if(dto.getTelephone()!=null) c.setTelephone(dto.getTelephone());
        if(dto.getTypeClient()!=null) c.setTypeClient(dto.getTypeClient());
        return clientRepository.save(c);
    }

    public void supprimerClient(Long clientId){
        clientRepository.deleteById(clientId);
    }

    @Transactional
    public void transfererClient(Long clientId, Long nouveauConseillerId){
        Client c = clientRepository.findById(clientId).orElseThrow();
        Conseiller newCons = conseillerRepository.findById(nouveauConseillerId).orElseThrow();
        if(!newCons.peutAjouterClient()){
            throw new IllegalStateException("Le nouveau conseiller a déjà 10 clients");
        }
        c.setConseiller(newCons);
    }

    @Transactional
    public void effectuerVirement(VirementDTO v){
        if(v.getMontant() <= 0) throw new IllegalArgumentException("Montant invalide");
        Client src = clientRepository.findById(v.getClientSourceId()).orElseThrow();
        Client dst = clientRepository.findById(v.getClientDestId()).orElseThrow();
        CompteCourant cSrc = src.getCompteCourant();
        CompteCourant cDst = dst.getCompteCourant();
        if(cSrc==null || cDst==null) throw new IllegalStateException("Les deux clients doivent avoir un compte courant");

        double futureSolde = cSrc.getSolde() - v.getMontant();
        double plafond = src.getTypeClient()== TypeClient.ENTREPRISES ? -50000d : -5000d;
        double decouvertMax = -Math.min(cSrc.getDecouvertAutorise(), Math.abs(plafond));
        if(futureSolde < decouvertMax){
            throw new IllegalStateException("Plafond de découvert dépassé");
        }

        cSrc.debiter(v.getMontant());
        cDst.crediter(v.getMontant());
    }

    public SimulationCreditDTO simulerCredit(double montant, int dureeMois, double tauxAnnuel, double assuranceAnnuel){
        double tauxMensuel = tauxAnnuel / 12.0 / 100.0;
        double assuranceMensuelle = assuranceAnnuel / 12.0 / 100.0;
        double mensualiteHorsAssurance;
        if (tauxMensuel == 0) {
            mensualiteHorsAssurance = montant / dureeMois;
        } else {
            double facteur = Math.pow(1 + tauxMensuel, -dureeMois);
            mensualiteHorsAssurance = montant * (tauxMensuel / (1 - facteur));
        }
        double mensualite = mensualiteHorsAssurance + montant * assuranceMensuelle;
        double coutTotal = mensualite * dureeMois - montant;

        SimulationCreditDTO dto = new SimulationCreditDTO();
        dto.setMontant(montant);
        dto.setDureeMois(dureeMois);
        dto.setTauxAnnuel(tauxAnnuel);
        dto.setAssuranceAnnuel(assuranceAnnuel);
        dto.setMensualite(Math.round(mensualite * 100.0) / 100.0);
        dto.setCoutTotal(Math.round(coutTotal * 100.0) / 100.0);
        return dto;
    }
}
