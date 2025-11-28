package com.example.simplecash.dto;

public class VirementDTO {
    private Long clientSourceId;
    private Long clientDestId;
    private double montant;

    public VirementDTO() {}

    public Long getClientSourceId() { return clientSourceId; }
    public void setClientSourceId(Long clientSourceId) { this.clientSourceId = clientSourceId; }
    public Long getClientDestId() { return clientDestId; }
    public void setClientDestId(Long clientDestId) { this.clientDestId = clientDestId; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
}

