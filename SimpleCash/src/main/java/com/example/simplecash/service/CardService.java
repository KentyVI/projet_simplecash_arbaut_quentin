package com.example.simplecash.service;

import com.example.simplecash.entity.Card;
import com.example.simplecash.entity.Client;
import com.example.simplecash.repository.CardRepository;
import com.example.simplecash.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;

    public CardService(CardRepository cardRepository, ClientRepository clientRepository) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
    }

    public List<Card> findAll(){ return cardRepository.findAll(); }
    public Card get(Long id){ return cardRepository.findById(id).orElseThrow(); }

    public Card createForClient(Long clientId, Card card){
        Client c = clientRepository.findById(clientId).orElseThrow();
        c.ajouterCarte(card);
        return cardRepository.save(card);
    }

    public void delete(Long id){ cardRepository.deleteById(id); }
}

