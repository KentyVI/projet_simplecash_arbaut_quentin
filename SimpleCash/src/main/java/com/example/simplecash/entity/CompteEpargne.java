package com.example.simplecash.entity;

import jakarta.persistence.Entity;

@Entity
public class CompteEpargne extends CompteBancaire {

    private Double tauxRemuneration = 0.03;
    public CompteEpargne() {}

    public Double getTauxRemuneration() {
        return tauxRemuneration;
    }

    public void setTauxRemuneration(Double tauxRemuneration) {
        this.tauxRemuneration = tauxRemuneration;
    }

    public Double calculerInterets() {
        return getSolde() * tauxRemuneration;
    }
}
