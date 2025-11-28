package com.example.simplecash.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conseiller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "gerant_id")
    private Gerant gerant;

    @OneToMany(mappedBy = "conseiller", cascade = CascadeType.ALL)
    private List<Client> clients = new ArrayList<>();

    public Conseiller() {}

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Gerant getGerant() {
        return gerant;
    }

    public void setGerant(Gerant gerant) {
        this.gerant = gerant;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public boolean peutAjouterClient() {
        return this.clients.size() < 10;
    }

    public void ajouterClient(Client client) {
        if (!peutAjouterClient()) {
            throw new IllegalStateException("Ce conseiller a déjà 10 clients !");
        }
        client.setConseiller(this);
        clients.add(client);
    }
}
