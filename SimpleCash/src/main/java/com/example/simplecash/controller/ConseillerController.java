package com.example.simplecash.controller;

import com.example.simplecash.dto.*;
import com.example.simplecash.entity.Client;
import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.ConseillerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conseillers")
public class ConseillerController {
    private final ConseillerService conseillerService;

    public ConseillerController(ConseillerService conseillerService) {
        this.conseillerService = conseillerService;
    }

    @GetMapping
    public List<ConseillerDTO> list(){
        return conseillerService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ConseillerDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(conseillerService.get(id)); }

    @PostMapping
    public ResponseEntity<ConseillerDTO> create(@RequestBody ConseillerDTO dto){
        Conseiller c = new Conseiller();
        c.setNom(dto.getNom()); c.setPrenom(dto.getPrenom()); c.setEmail(dto.getEmail()); c.setTelephone(dto.getTelephone());
        Conseiller created = conseillerService.create(c, dto.getManagerId());
        return ResponseEntity.created(URI.create("/api/conseillers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ConseillerDTO update(@PathVariable Long id, @RequestBody ConseillerDTO dto){
        Conseiller payload = new Conseiller();
        payload.setNom(dto.getNom()); payload.setPrenom(dto.getPrenom()); payload.setEmail(dto.getEmail()); payload.setTelephone(dto.getTelephone());
        return SimpleCashMapper.toDTO(conseillerService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ conseillerService.delete(id); }

    // Gestion client par conseiller
    @PostMapping("/{id}/clients")
    public ResponseEntity<ClientDTO> createClient(@PathVariable Long id, @RequestBody ClientCreateDTO dto){
        Client created = conseillerService.creerClient(id, dto);
        return ResponseEntity.created(URI.create("/api/clients/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @PutMapping("/clients/{clientId}")
    public ClientDTO updateClient(@PathVariable Long clientId, @RequestBody ClientCreateDTO dto){
        return SimpleCashMapper.toDTO(conseillerService.modifierClient(clientId, dto));
    }

    @DeleteMapping("/clients/{clientId}")
    public void deleteClient(@PathVariable Long clientId){ conseillerService.supprimerClient(clientId); }

    @PostMapping("/transfer")
    public void transferClient(@RequestBody TransferClientDTO payload){
        conseillerService.transfererClient(payload.getClientId(), payload.getNouveauConseillerId());
    }

    @PostMapping("/virements")
    public void virement(@RequestBody VirementDTO v){ conseillerService.effectuerVirement(v); }

    @GetMapping("/simuler-credit")
    public SimulationCreditDTO simulerCredit(@RequestParam double montant,
                                             @RequestParam int dureeMois,
                                             @RequestParam double tauxAnnuel,
                                             @RequestParam(defaultValue = "0") double assuranceAnnuel){
        return conseillerService.simulerCredit(montant, dureeMois, tauxAnnuel, assuranceAnnuel);
    }
}
