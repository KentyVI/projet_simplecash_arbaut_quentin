package com.example.simplecash.service;

import com.example.simplecash.entity.Agence;
import com.example.simplecash.entity.Manager;
import com.example.simplecash.repository.AgenceRepository;
import com.example.simplecash.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgenceService {
    private final AgenceRepository agenceRepository;
    private final ManagerRepository managerRepository;

    public AgenceService(AgenceRepository agenceRepository, ManagerRepository managerRepository) {
        this.agenceRepository = agenceRepository;
        this.managerRepository = managerRepository;
    }

    public List<Agence> findAll(){ return agenceRepository.findAll(); }

    public Agence get(String id){ return agenceRepository.findById(id).orElseThrow(); }

    public Agence create(Agence a, Long managerId){
        if(managerId!=null){
            Manager g = managerRepository.findById(managerId).orElseThrow();
            a.setManager(g);
        }
        return agenceRepository.save(a);
    }

    public Agence update(String id, Agence payload, Long managerId){
        Agence a = get(id);
        a.setDateCreation(payload.getDateCreation());
        if(managerId!=null){
            Manager g = managerRepository.findById(managerId).orElseThrow();
            a.setManager(g);
        }
        return agenceRepository.save(a);
    }

    @Transactional
    public void delete(String id){
        Agence a = agenceRepository.findById(id).orElse(null);
        if(a==null) return;
        if(a.getManager()!=null){
            Manager g = a.getManager();
            g.setAgence(null); // break inverse side
            a.setManager(null); // break owning side
        }
        agenceRepository.delete(a);
    }
}
