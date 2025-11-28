package com.example.simplecash.controller;

import com.example.simplecash.dto.CardDTO;
import com.example.simplecash.entity.Card;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cartes")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardDTO> list(){
        return cardService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CardDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(cardService.get(id)); }

    @PostMapping("/client/{clientId}")
    public ResponseEntity<CardDTO> create(@PathVariable Long clientId, @RequestBody CardDTO dto){
        Card c = new Card();
        c.setType(dto.getType());
        c.setNumeroCarte(dto.getNumeroCarte());
        c.setDateExpiration(dto.getDateExpiration());
        Card created = cardService.createForClient(clientId, c);
        return ResponseEntity.created(URI.create("/api/cartes/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ cardService.delete(id); }
}
