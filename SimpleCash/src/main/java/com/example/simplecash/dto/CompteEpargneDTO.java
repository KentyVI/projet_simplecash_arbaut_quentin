package com.example.simplecash.dto;

import java.time.LocalDate;

public class CompteEpargneDTO {
    private Long id;
    private String numeroCompte;
    private Double solde;
    private LocalDate dateOuverture;
    private Double tauxRemuneration;

    public CompteEpargneDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }
    public Double getSolde() { return solde; }
    public void setSolde(Double solde) { this.solde = solde; }
    public LocalDate getDateOuverture() { return dateOuverture; }
    public void setDateOuverture(LocalDate dateOuverture) { this.dateOuverture = dateOuverture; }
    public Double getTauxRemuneration() { return tauxRemuneration; }
    public void setTauxRemuneration(Double tauxRemuneration) { this.tauxRemuneration = tauxRemuneration; }
}

