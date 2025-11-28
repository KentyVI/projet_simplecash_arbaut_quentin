package com.example.simplecash.service;

import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.entity.Gerant;
import com.example.simplecash.repository.ConseillerRepository;
import com.example.simplecash.repository.GerantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerantService {
    private final GerantRepository gerantRepository;
    private final ConseillerRepository conseillerRepository;

    public GerantService(GerantRepository gerantRepository, ConseillerRepository conseillerRepository) {
        this.gerantRepository = gerantRepository;
        this.conseillerRepository = conseillerRepository;
    }

    public List<Gerant> findAll(){ return gerantRepository.findAll(); }
    public Gerant get(Long id){ return gerantRepository.findById(id).orElseThrow(); }
    public Gerant create(Gerant g){ return gerantRepository.save(g); }
    public Gerant update(Long id, Gerant payload){
        Gerant g = get(id);
        g.setNom(payload.getNom());
        g.setPrenom(payload.getPrenom());
        g.setEmail(payload.getEmail());
        g.setTelephone(payload.getTelephone());
        return gerantRepository.save(g);
    }
    public void delete(Long id){ gerantRepository.deleteById(id); }

    public Conseiller addConseiller(Long gerantId, Conseiller conseiller){
        Gerant g = get(gerantId);
        conseiller.setGerant(g);
        return conseillerRepository.save(conseiller);
    }
}

