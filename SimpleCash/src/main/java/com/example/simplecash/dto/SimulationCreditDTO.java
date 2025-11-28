package com.example.simplecash.dto;

public class SimulationCreditDTO {
    private double montant;
    private int dureeMois;
    private double tauxAnnuel;
    private double assuranceAnnuel;
    private double mensualite;
    private double coutTotal;

    public SimulationCreditDTO() {}

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public int getDureeMois() { return dureeMois; }
    public void setDureeMois(int dureeMois) { this.dureeMois = dureeMois; }
    public double getTauxAnnuel() { return tauxAnnuel; }
    public void setTauxAnnuel(double tauxAnnuel) { this.tauxAnnuel = tauxAnnuel; }
    public double getAssuranceAnnuel() { return assuranceAnnuel; }
    public void setAssuranceAnnuel(double assuranceAnnuel) { this.assuranceAnnuel = assuranceAnnuel; }
    public double getMensualite() { return mensualite; }
    public void setMensualite(double mensualite) { this.mensualite = mensualite; }
    public double getCoutTotal() { return coutTotal; }
    public void setCoutTotal(double coutTotal) { this.coutTotal = coutTotal; }
}

