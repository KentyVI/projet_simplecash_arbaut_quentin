package com.example.simplecash.controller;

import com.example.simplecash.dto.ClientDTO;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Gestion des clients et de leurs comptes")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Lister les clients")
    @GetMapping
    public List<ClientDTO> list(){
        return clientService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Récupérer un client par id")
    @GetMapping("/{id}")
    public ClientDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(clientService.get(id)); }

    @Operation(summary = "Ajouter un compte courant au client")
    @PostMapping("/{id}/compte-courant")
    public ClientDTO addCompteCourant(@PathVariable Long id, @RequestBody Map<String, Object> body){
        String numero = (String) body.getOrDefault("numeroCompte", "");
        Double solde = body.get("soldeInitial")!=null? ((Number)body.get("soldeInitial")).doubleValue() : 0d;
        Double decouvert = body.get("decouvertAutorise")!=null? ((Number)body.get("decouvertAutorise")).doubleValue() : null;
        return SimpleCashMapper.toDTO(clientService.ajouterCompteCourant(id, numero, solde, decouvert));
    }

    @Operation(summary = "Ajouter un compte épargne au client")
    @PostMapping("/{id}/compte-epargne")
    public ClientDTO addCompteEpargne(@PathVariable Long id, @RequestBody Map<String, Object> body){
        String numero = (String) body.getOrDefault("numeroCompte", "");
        Double solde = body.get("soldeInitial")!=null? ((Number)body.get("soldeInitial")).doubleValue() : 0d;
        Double taux = body.get("tauxRemuneration")!=null? ((Number)body.get("tauxRemuneration")).doubleValue() : null;
        return SimpleCashMapper.toDTO(clientService.ajouterCompteEpargne(id, numero, solde, taux));
    }
}
