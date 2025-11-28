package com.example.simplecash.service;

import com.example.simplecash.entity.Agence;
import com.example.simplecash.entity.Banque;
import com.example.simplecash.entity.Gerant;
import com.example.simplecash.repository.AgenceRepository;
import com.example.simplecash.repository.BanqueRepository;
import com.example.simplecash.repository.GerantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgenceService {
    private final AgenceRepository agenceRepository;
    private final BanqueRepository banqueRepository;
    private final GerantRepository gerantRepository;

    public AgenceService(AgenceRepository agenceRepository, BanqueRepository banqueRepository, GerantRepository gerantRepository) {
        this.agenceRepository = agenceRepository;
        this.banqueRepository = banqueRepository;
        this.gerantRepository = gerantRepository;
    }

    public List<Agence> findAll(){ return agenceRepository.findAll(); }

    public Agence get(String id){ return agenceRepository.findById(id).orElseThrow(); }

    public Agence create(Agence a, Long banqueId, Long gerantId){
        if(banqueId!=null){
            Banque b = banqueRepository.findById(banqueId).orElseThrow();
            a.setBanque(b);
        }
        if(gerantId!=null){
            Gerant g = gerantRepository.findById(gerantId).orElseThrow();
            // Owning side is Agence. Only set it here to avoid transient reference on Gerant
            a.setGerant(g);
        }
        return agenceRepository.save(a);
    }

    public Agence update(String id, Agence payload, Long banqueId, Long gerantId){
        Agence a = get(id);
        a.setDateCreation(payload.getDateCreation());
        if(banqueId!=null){
            Banque b = banqueRepository.findById(banqueId).orElseThrow();
            a.setBanque(b);
        }
        if(gerantId!=null){
            Gerant g = gerantRepository.findById(gerantId).orElseThrow();
            a.setGerant(g);
        }
        return agenceRepository.save(a);
    }

    @Transactional
    public void delete(String id){
        Agence a = agenceRepository.findById(id).orElse(null);
        if(a==null) return;
        if(a.getGerant()!=null){
            Gerant g = a.getGerant();
            g.setAgence(null); // break inverse side
            a.setGerant(null); // break owning side
        }
        a.setBanque(null);
        agenceRepository.delete(a);
    }
}
