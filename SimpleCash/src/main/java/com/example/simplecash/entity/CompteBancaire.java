package com.example.simplecash.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class CompteBancaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCompte;

    private Double solde;

    private LocalDate dateOuverture;

    public CompteBancaire() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }


    public void crediter(Double montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        this.solde += montant;
    }

    public void debiter(Double montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        this.solde -= montant;
    }
}
