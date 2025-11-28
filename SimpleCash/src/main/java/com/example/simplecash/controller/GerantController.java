package com.example.simplecash.controller;

import com.example.simplecash.dto.ConseillerDTO;
import com.example.simplecash.dto.GerantDTO;
import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.entity.Gerant;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.GerantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gerants")
public class GerantController {
    private final GerantService gerantService;

    public GerantController(GerantService gerantService) {
        this.gerantService = gerantService;
    }

    @GetMapping
    public List<GerantDTO> list(){
        return gerantService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GerantDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(gerantService.get(id)); }

    @PostMapping
    public ResponseEntity<GerantDTO> create(@RequestBody GerantDTO dto){
        Gerant g = new Gerant();
        g.setNom(dto.getNom()); g.setPrenom(dto.getPrenom());
        g.setEmail(dto.getEmail()); g.setTelephone(dto.getTelephone());
        Gerant created = gerantService.create(g);
        return ResponseEntity.created(URI.create("/api/gerants/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public GerantDTO update(@PathVariable Long id, @RequestBody GerantDTO dto){
        Gerant payload = new Gerant();
        payload.setNom(dto.getNom()); payload.setPrenom(dto.getPrenom());
        payload.setEmail(dto.getEmail()); payload.setTelephone(dto.getTelephone());
        return SimpleCashMapper.toDTO(gerantService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ gerantService.delete(id); }

    @PostMapping("/{id}/conseillers")
    public ResponseEntity<ConseillerDTO> addConseiller(@PathVariable Long id, @RequestBody ConseillerDTO dto){
        Conseiller c = new Conseiller();
        c.setNom(dto.getNom()); c.setPrenom(dto.getPrenom()); c.setEmail(dto.getEmail()); c.setTelephone(dto.getTelephone());
        Conseiller created = gerantService.addConseiller(id, c);
        return ResponseEntity.created(URI.create("/api/conseillers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }
}

