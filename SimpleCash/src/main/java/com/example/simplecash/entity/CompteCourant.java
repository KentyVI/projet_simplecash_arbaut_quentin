package com.example.simplecash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class CompteCourant extends CompteBancaire {

    private Double decouvertAutorise = 1000.0;

    public CompteCourant() {}

    public Double getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(Double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    public boolean peutDebiter(Double montant) {
        return getSolde() - montant >= -decouvertAutorise;
    }
}
