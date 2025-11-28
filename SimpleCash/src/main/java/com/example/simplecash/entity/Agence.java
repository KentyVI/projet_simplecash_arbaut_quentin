package com.example.simplecash.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Agence {

    @Id
    private String id;

    private LocalDate dateCreation;

    @OneToOne
    private Manager manager;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }
}
