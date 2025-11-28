package com.example.simplecash.controller;

import com.example.simplecash.dto.ConseillerDTO;
import com.example.simplecash.dto.ManagerDTO;
import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.entity.Manager;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/managers")
@Tag(name = "Managers", description = "Gestion des managers et actions globales")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Operation(summary = "Lister les managers")
    @GetMapping
    public List<ManagerDTO> list(){
        return managerService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Récupérer un manager par id")
    @GetMapping("/{id}")
    public ManagerDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(managerService.get(id)); }

    @Operation(summary = "Créer un manager")
    @PostMapping
    public ResponseEntity<ManagerDTO> create(@RequestBody ManagerDTO dto){
        Manager manager = new Manager();
        manager.setNom(dto.getNom()); manager.setPrenom(dto.getPrenom());
        manager.setEmail(dto.getEmail()); manager.setTelephone(dto.getTelephone());
        Manager created = managerService.create(manager);
        return ResponseEntity.created(URI.create("/api/managers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @Operation(summary = "Modifier un manager")
    @PutMapping("/{id}")
    public ManagerDTO update(@PathVariable Long id, @RequestBody ManagerDTO dto){
        Manager payload = new Manager();
        payload.setNom(dto.getNom()); payload.setPrenom(dto.getPrenom());
        payload.setEmail(dto.getEmail()); payload.setTelephone(dto.getTelephone());
        return SimpleCashMapper.toDTO(managerService.update(id, payload));
    }

    @Operation(summary = "Supprimer un manager")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ managerService.delete(id); }

    @Operation(summary = "Ajouter un conseiller à un manager")
    @PostMapping("/{id}/conseillers")
    public ResponseEntity<ConseillerDTO> addConseiller(@PathVariable Long id, @RequestBody ConseillerDTO dto){
        Conseiller conseiller = new Conseiller();
        conseiller.setNom(dto.getNom()); conseiller.setPrenom(dto.getPrenom()); conseiller.setEmail(dto.getEmail()); conseiller.setTelephone(dto.getTelephone());
        Conseiller created = managerService.addConseiller(id, conseiller);
        return ResponseEntity.created(URI.create("/api/conseillers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @Operation(summary = "Lancer l'audit des comptes", description = "Vérifie les plafonds de découvert pour tous les clients")
    @GetMapping("/audit")
    public List<String> lancerAudit(){
        return managerService.auditerComptes();
    }
}
