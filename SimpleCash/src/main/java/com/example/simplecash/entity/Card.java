package com.example.simplecash.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeCard type;  // VISA_ELECTRON, VISA_PREMIER

    private String numeroCarte;

    private LocalDate dateExpiration;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeCard getType() {
        return type;
    }

    public void setType(TypeCard type) {
        this.type = type;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
