package com.example.simplecash.service;

import com.example.simplecash.entity.Banque;
import com.example.simplecash.repository.BanqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanqueService {
    private final BanqueRepository banqueRepository;

    public BanqueService(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    public List<Banque> findAll(){
        return banqueRepository.findAll();
    }

    public Banque get(Long id){
        return banqueRepository.findById(id).orElseThrow();
    }

    public Banque create(Banque b){
        return banqueRepository.save(b);
    }

    public Banque update(Long id, Banque payload){
        Banque b = get(id);
        b.setNom(payload.getNom());
        b.setAdresseSiege(payload.getAdresseSiege());
        b.setTelephone(payload.getTelephone());
        return banqueRepository.save(b);
    }

    public void delete(Long id){
        banqueRepository.deleteById(id);
    }
}

