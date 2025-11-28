package com.example.simplecash.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class VirementDTO {

    @Schema(description = "ID du client émetteur", example = "1")
    private Long clientSourceId;

    @Schema(description = "ID du client bénéficiaire", example = "2")
    private Long clientDestId;

    @Schema(description = "Montant à transférer", example = "500.00")
    private double montant;

    public VirementDTO() {}

    public Long getClientSourceId() { return clientSourceId; }
    public void setClientSourceId(Long clientSourceId) { this.clientSourceId = clientSourceId; }
    public Long getClientDestId() { return clientDestId; }
    public void setClientDestId(Long clientDestId) { this.clientDestId = clientDestId; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
}
