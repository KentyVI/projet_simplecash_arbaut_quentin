package com.example.simplecash.controller;

import com.example.simplecash.dto.*;
import com.example.simplecash.entity.Client;
import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.ConseillerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conseillers")
@Tag(name = "Espace Conseiller", description = "Gestion des conseillers, de leurs clients et des opérations bancaires")
public class ConseillerController {
    private final ConseillerService conseillerService;

    public ConseillerController(ConseillerService conseillerService) {
        this.conseillerService = conseillerService;
    }

    @Operation(summary = "Lister les conseillers", description = "Récupère la liste complète de tous les conseillers.")
    @GetMapping
    public List<ConseillerDTO> list(){
        return conseillerService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Récupérer un conseiller par son id")
    @GetMapping("/{id}")
    public ConseillerDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(conseillerService.get(id)); }

    @Operation(summary = "Créer un conseiller", description = "Ajoute un nouveau conseiller et l'assigne à un manager.")
    @PostMapping
    public ResponseEntity<ConseillerDTO> create(@RequestBody ConseillerDTO dto){
        Conseiller c = new Conseiller();
        c.setNom(dto.getNom()); c.setPrenom(dto.getPrenom()); c.setEmail(dto.getEmail()); c.setTelephone(dto.getTelephone());
        Conseiller created = conseillerService.create(c, dto.getManagerId());
        return ResponseEntity.created(URI.create("/api/conseillers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @Operation(summary = "Modifier un conseiller")
    @PutMapping("/{id}")
    public ConseillerDTO update(@PathVariable Long id, @RequestBody ConseillerDTO dto){
        Conseiller payload = new Conseiller();
        payload.setNom(dto.getNom()); payload.setPrenom(dto.getPrenom()); payload.setEmail(dto.getEmail()); payload.setTelephone(dto.getTelephone());
        return SimpleCashMapper.toDTO(conseillerService.update(id, payload));
    }

    @Operation(summary = "Supprimer un conseiller")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ conseillerService.delete(id); }

    // Gestion client par conseiller
    @Operation(summary = "Créer un client pour un conseiller")
    @PostMapping("/{id}/clients")
    public ResponseEntity<ClientDTO> createClient(@PathVariable Long id, @RequestBody ClientCreateDTO dto){
        Client created = conseillerService.creerClient(id, dto);
        return ResponseEntity.created(URI.create("/api/clients/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @Operation(summary = "Modifier un client rattaché à un conseiller")
    @PutMapping("/clients/{clientId}")
    public ClientDTO updateClient(@PathVariable Long clientId, @RequestBody ClientCreateDTO dto){
        return SimpleCashMapper.toDTO(conseillerService.modifierClient(clientId, dto));
    }

    @Operation(summary = "Supprimer un client rattaché à un conseiller")
    @DeleteMapping("/clients/{clientId}")
    public void deleteClient(@PathVariable Long clientId){ conseillerService.supprimerClient(clientId); }

    @Operation(summary = "Transférer un client à un autre conseiller")
    @PostMapping("/transfer")
    public void transferClient(@RequestBody TransferClientDTO payload){
        conseillerService.transfererClient(payload.getClientId(), payload.getNouveauConseillerId());
    }

    @Operation(summary = "Effectuer un virement", description = "Transfère de l'argent d'un compte à un autre. Vérifie les plafonds de découvert.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Virement effectué avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Solde insuffisant ou compte introuvable")
    })
    @PostMapping("/virements")
    public void virement(@RequestBody VirementDTO v){ conseillerService.effectuerVirement(v); }

    @Operation(summary = "Simuler un crédit", description = "Calcule les mensualités et le coût total d'un crédit.")
    @GetMapping("/simuler-credit")
    public SimulationCreditDTO simulerCredit(
            @Parameter(description = "Montant emprunté", example = "10000") @RequestParam double montant,
            @Parameter(description = "Durée en mois", example = "24") @RequestParam int dureeMois,
            @Parameter(description = "Taux annuel en %", example = "3.5") @RequestParam double tauxAnnuel,
            @Parameter(description = "Taux assurance annuel en %", example = "0.3") @RequestParam(defaultValue = "0") double assuranceAnnuel){
        return conseillerService.simulerCredit(montant, dureeMois, tauxAnnuel, assuranceAnnuel);
    }
}
