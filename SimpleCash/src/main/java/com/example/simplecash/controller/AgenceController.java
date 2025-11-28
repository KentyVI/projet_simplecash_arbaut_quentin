package com.example.simplecash.controller;

import com.example.simplecash.dto.AgenceDTO;
import com.example.simplecash.entity.Agence;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.AgenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agences")
@Tag(name = "Agences", description = "Gestion des agences bancaires")
public class AgenceController {
    private final AgenceService agenceService;

    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @Operation(summary = "Lister les agences")
    @GetMapping
    public List<AgenceDTO> list(){
        return agenceService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Récupérer une agence par id")
    @GetMapping("/{id}")
    public AgenceDTO get(@PathVariable String id){ return SimpleCashMapper.toDTO(agenceService.get(id)); }

    @Operation(summary = "Créer une agence")
    @PostMapping
    public ResponseEntity<AgenceDTO> create(@RequestBody AgenceDTO dto){
        Agence agence = new Agence();
        agence.setId(dto.getId());
        agence.setDateCreation(dto.getDateCreation());
        Agence created = agenceService.create(agence, dto.getManagerId());
        return ResponseEntity.created(URI.create("/api/agences/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @Operation(summary = "Modifier une agence")
    @PutMapping("/{id}")
    public AgenceDTO update(@PathVariable String id, @RequestBody AgenceDTO dto){
        Agence agence = new Agence();
        agence.setDateCreation(dto.getDateCreation());
        return SimpleCashMapper.toDTO(agenceService.update(id, agence, dto.getManagerId()));
    }

    @Operation(summary = "Supprimer une agence")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){ agenceService.delete(id); }
}
