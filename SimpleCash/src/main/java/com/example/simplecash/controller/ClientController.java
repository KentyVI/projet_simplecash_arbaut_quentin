package com.example.simplecash.controller;

import com.example.simplecash.dto.ClientDTO;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDTO> list(){
        return clientService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(clientService.get(id)); }

    @PostMapping("/{id}/compte-courant")
    public ClientDTO addCompteCourant(@PathVariable Long id, @RequestBody Map<String, Object> body){
        String numero = (String) body.getOrDefault("numeroCompte", "");
        Double solde = body.get("soldeInitial")!=null? ((Number)body.get("soldeInitial")).doubleValue() : 0d;
        Double decouvert = body.get("decouvertAutorise")!=null? ((Number)body.get("decouvertAutorise")).doubleValue() : null;
        return SimpleCashMapper.toDTO(clientService.ajouterCompteCourant(id, numero, solde, decouvert));
    }

    @PostMapping("/{id}/compte-epargne")
    public ClientDTO addCompteEpargne(@PathVariable Long id, @RequestBody Map<String, Object> body){
        String numero = (String) body.getOrDefault("numeroCompte", "");
        Double solde = body.get("soldeInitial")!=null? ((Number)body.get("soldeInitial")).doubleValue() : 0d;
        Double taux = body.get("tauxRemuneration")!=null? ((Number)body.get("tauxRemuneration")).doubleValue() : null;
        return SimpleCashMapper.toDTO(clientService.ajouterCompteEpargne(id, numero, solde, taux));
    }
}

