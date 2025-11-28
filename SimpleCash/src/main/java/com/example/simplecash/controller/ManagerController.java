package com.example.simplecash.controller;

import com.example.simplecash.dto.ConseillerDTO;
import com.example.simplecash.dto.ManagerDTO;
import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.entity.Manager;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public List<ManagerDTO> list(){
        return managerService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ManagerDTO get(@PathVariable Long id){ return SimpleCashMapper.toDTO(managerService.get(id)); }

    @PostMapping
    public ResponseEntity<ManagerDTO> create(@RequestBody ManagerDTO dto){
        Manager g = new Manager();
        g.setNom(dto.getNom()); g.setPrenom(dto.getPrenom());
        g.setEmail(dto.getEmail()); g.setTelephone(dto.getTelephone());
        Manager created = managerService.create(g);
        return ResponseEntity.created(URI.create("/api/managers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ManagerDTO update(@PathVariable Long id, @RequestBody ManagerDTO dto){
        Manager payload = new Manager();
        payload.setNom(dto.getNom()); payload.setPrenom(dto.getPrenom());
        payload.setEmail(dto.getEmail()); payload.setTelephone(dto.getTelephone());
        return SimpleCashMapper.toDTO(managerService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ managerService.delete(id); }

    @PostMapping("/{id}/conseillers")
    public ResponseEntity<ConseillerDTO> addConseiller(@PathVariable Long id, @RequestBody ConseillerDTO dto){
        Conseiller c = new Conseiller();
        c.setNom(dto.getNom()); c.setPrenom(dto.getPrenom()); c.setEmail(dto.getEmail()); c.setTelephone(dto.getTelephone());
        Conseiller created = managerService.addConseiller(id, c);
        return ResponseEntity.created(URI.create("/api/conseillers/"+created.getId())).body(SimpleCashMapper.toDTO(created));
    }
}
