package com.example.simplecash.entity;

import com.example.simplecash.entity.Banque;
import com.example.simplecash.entity.Gerant;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String id;

    private LocalDate dateCreation;

    @ManyToOne
    @JoinColumn(name = "banque_id")
    private Banque banque;

    @OneToOne
    private Gerant gerant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public Gerant getGerant() {
        return gerant;
    }

    public void setGerant(Gerant gerant) {
        this.gerant = gerant;
    }

    // getters/setters/constructors
}
