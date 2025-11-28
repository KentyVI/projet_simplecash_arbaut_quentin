package com.example.simplecash.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Banque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresseSiege;
    private String telephone;

    @OneToMany(mappedBy = "banque", cascade = CascadeType.ALL)
    private List<Agence> agences;

    public Banque() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresseSiege() {
        return adresseSiege;
    }

    public void setAdresseSiege(String adresseSiege) {
        this.adresseSiege = adresseSiege;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Agence> getAgences() {
        return agences;
    }

    public void setAgences(List<Agence> agences) {
        this.agences = agences;
    }
}
