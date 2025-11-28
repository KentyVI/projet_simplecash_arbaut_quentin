package com.example.simplecash.mapper;

import com.example.simplecash.dto.*;
import com.example.simplecash.entity.*;

public final class SimpleCashMapper {
    private SimpleCashMapper() {}

    public static BanqueDTO toDTO(Banque banque){
        if(banque==null) 
            return null;
        BanqueDTO banqueDTO = new BanqueDTO();
        banqueDTO.setId(banque.getId());
        banqueDTO.setNom(banque.getNom());
        banqueDTO.setAdresseSiege(banque.getAdresseSiege());
        banqueDTO.setTelephone(banque.getTelephone());
        return banqueDTO;
    }

    public static GerantDTO toDTO(Gerant gerant){
        if(gerant==null)
            return null;
        GerantDTO gerantDTO = new GerantDTO();
        gerantDTO.setId(gerant.getId());
        gerantDTO.setNom(gerant.getNom());
        gerantDTO.setPrenom(gerant.getPrenom());
        gerantDTO.setEmail(gerant.getEmail());
        gerantDTO.setTelephone(gerant.getTelephone());
        gerantDTO.setAgenceId(gerant.getAgence()!=null? gerant.getAgence().getId():null);
        return gerantDTO;
    }

    public static ConseillerDTO toDTO(Conseiller conseiller){
        if(conseiller==null)
            return null;
        ConseillerDTO conseillerDTO = new ConseillerDTO();
        conseillerDTO.setId(conseiller.getId());
        conseillerDTO.setNom(conseiller.getNom());
        conseillerDTO.setPrenom(conseiller.getPrenom());
        conseillerDTO.setEmail(conseiller.getEmail());
        conseillerDTO.setTelephone(conseiller.getTelephone());
        conseillerDTO.setGerantId(conseiller.getGerant()!=null? conseiller.getGerant().getId():null);
        return conseillerDTO;
    }

    public static ClientDTO toDTO(Client client){
        if(client==null)
            return null;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setNom(client.getNom());
        clientDTO.setPrenom(client.getPrenom());
        clientDTO.setAdresse(client.getAdresse());
        clientDTO.setCodePostal(client.getCodePostal());
        clientDTO.setVille(client.getVille());
        clientDTO.setTelephone(client.getTelephone());
        clientDTO.setTypeClient(client.getTypeClient());
        clientDTO.setConseillerId(client.getConseiller()!=null? client.getConseiller().getId():null);
        clientDTO.setCompteCourantId(client.getCompteCourant()!=null? client.getCompteCourant().getId():null);
        clientDTO.setCompteEpargneId(client.getCompteEpargne()!=null? client.getCompteEpargne().getId():null);
        return clientDTO;
    }

    public static AgenceDTO toDTO(Agence agence){
        if(agence==null)
            return null;
        AgenceDTO agenceDTO = new AgenceDTO();
        agenceDTO.setId(agence.getId());
        agenceDTO.setDateCreation(agence.getDateCreation());
        agenceDTO.setBanqueId(agence.getBanque()!=null ? agence.getBanque().getId() : null);
        agenceDTO.setGerantId(agence.getGerant()!=null ? agence.getGerant().getId() : null);
        return agenceDTO;
    }

    public static CardDTO toDTO(Card card){
        if(card==null)
            return null;
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setType(card.getType());
        cardDTO.setNumeroCarte(card.getNumeroCarte());
        cardDTO.setDateExpiration(card.getDateExpiration());
        cardDTO.setClientId(card.getClient()!=null? card.getClient().getId():null);
        return cardDTO;
    }

    public static CompteCourantDTO toDTO(CompteCourant compteCourant){
        if(compteCourant==null)
            return null;
        CompteCourantDTO compteCourantDTO = new CompteCourantDTO();
        compteCourantDTO.setId(compteCourant.getId());
        compteCourantDTO.setNumeroCompte(compteCourant.getNumeroCompte());
        compteCourantDTO.setSolde(compteCourant.getSolde());
        compteCourantDTO.setDateOuverture(compteCourant.getDateOuverture());
        compteCourantDTO.setDecouvertAutorise(compteCourant.getDecouvertAutorise());
        return compteCourantDTO;
    }

    public static CompteEpargneDTO toDTO(CompteEpargne compteEpargne){
        if(compteEpargne==null) return null;
        CompteEpargneDTO compteEpargneDTO = new CompteEpargneDTO();
        compteEpargneDTO.setId(compteEpargne.getId());
        compteEpargneDTO.setNumeroCompte(compteEpargne.getNumeroCompte());
        compteEpargneDTO.setSolde(compteEpargne.getSolde());
        compteEpargneDTO.setDateOuverture(compteEpargne.getDateOuverture());
        compteEpargneDTO.setTauxRemuneration(compteEpargne.getTauxRemuneration());
        return compteEpargneDTO;
    }
}

