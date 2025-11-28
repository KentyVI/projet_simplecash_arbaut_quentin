package com.example.simplecash.dto;

import com.example.simplecash.entity.TypeCard;
import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private TypeCard type;
    private String numeroCarte;
    private LocalDate dateExpiration;
    private Long clientId;

    public CardDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TypeCard getType() { return type; }
    public void setType(TypeCard type) { this.type = type; }
    public String getNumeroCarte() { return numeroCarte; }
    public void setNumeroCarte(String numeroCarte) { this.numeroCarte = numeroCarte; }
    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
}

