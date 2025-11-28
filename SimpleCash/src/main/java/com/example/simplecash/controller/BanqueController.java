package com.example.simplecash.controller;

import com.example.simplecash.dto.BanqueDTO;
import com.example.simplecash.entity.Banque;
import com.example.simplecash.mapper.SimpleCashMapper;
import com.example.simplecash.service.BanqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banques")
public class BanqueController {
    private final BanqueService banqueService;

    public BanqueController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @GetMapping
    public List<BanqueDTO> list(){
        return banqueService.findAll().stream().map(SimpleCashMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BanqueDTO get(@PathVariable Long id){
        return SimpleCashMapper.toDTO(banqueService.get(id));
    }

    @PostMapping
    public ResponseEntity<BanqueDTO> create(@RequestBody BanqueDTO dto){
        Banque b = new Banque();
        b.setNom(dto.getNom());
        b.setAdresseSiege(dto.getAdresseSiege());
        b.setTelephone(dto.getTelephone());
        Banque created = banqueService.create(b);
        return ResponseEntity.created(URI.create("/api/banques/" + created.getId()))
                .body(SimpleCashMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public BanqueDTO update(@PathVariable Long id, @RequestBody BanqueDTO dto){
        Banque payload = new Banque();
        payload.setNom(dto.getNom());
        payload.setAdresseSiege(dto.getAdresseSiege());
        payload.setTelephone(dto.getTelephone());
        return SimpleCashMapper.toDTO(banqueService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ banqueService.delete(id); }
}

