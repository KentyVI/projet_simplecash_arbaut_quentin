package com.example.simplecash.service;

import com.example.simplecash.entity.Conseiller;
import com.example.simplecash.entity.Manager;
import com.example.simplecash.repository.ConseillerRepository;
import com.example.simplecash.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ConseillerRepository conseillerRepository;

    public ManagerService(ManagerRepository managerRepository, ConseillerRepository conseillerRepository) {
        this.managerRepository = managerRepository;
        this.conseillerRepository = conseillerRepository;
    }

    public List<Manager> findAll(){ return managerRepository.findAll(); }
    public Manager get(Long id){ return managerRepository.findById(id).orElseThrow(); }
    public Manager create(Manager g){ return managerRepository.save(g); }
    public Manager update(Long id, Manager payload){
        Manager g = get(id);
        g.setNom(payload.getNom());
        g.setPrenom(payload.getPrenom());
        g.setEmail(payload.getEmail());
        g.setTelephone(payload.getTelephone());
        return managerRepository.save(g);
    }
    public void delete(Long id){ managerRepository.deleteById(id); }

    public Conseiller addConseiller(Long managerId, Conseiller conseiller){
        Manager g = get(managerId);
        conseiller.setManager(g);
        return conseillerRepository.save(conseiller);
    }
}
