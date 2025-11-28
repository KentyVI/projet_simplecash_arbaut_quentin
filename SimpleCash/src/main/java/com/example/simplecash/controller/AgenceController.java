package com.example.simplecash.controller;

import com.example.simplecash.dto.AgenceDTO;
import com.example.simplecash.entity.Agence;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.AgenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {
    private final AgenceService agenceService;

    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @GetMapping
    public List<AgenceDTO> list(){
        return agenceService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AgenceDTO get(@PathVariable String id){ return SimpleCashMapper.toDTO(agenceService.get(id)); }

    @PostMapping
    public ResponseEntity<AgenceDTO> create(@RequestBody AgenceDTO dto){
        Agence a = new Agence();
        a.setId(dto.getId());
        a.setDateCreation(dto.getDateCreation());
        Agence created = agenceService.create(a, dto.getBanqueId(), dto.getGerantId());
        return ResponseEntity.created(URI.create("/api/agences/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public AgenceDTO update(@PathVariable String id, @RequestBody AgenceDTO dto){
        Agence a = new Agence();
        a.setDateCreation(dto.getDateCreation());
        return SimpleCashMapper.toDTO(agenceService.update(id, a, dto.getBanqueId(), dto.getGerantId()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){ agenceService.delete(id); }
}

