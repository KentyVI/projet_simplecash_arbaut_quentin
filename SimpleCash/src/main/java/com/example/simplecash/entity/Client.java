package com.example.simplecash.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private TypeClient typeClient;

    @ManyToOne
    @JoinColumn(name = "conseiller_id")
    private Conseiller conseiller;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_courant_id")
    private CompteCourant compteCourant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_epargne_id")
    private CompteEpargne compteEpargne;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Card> cartes = new ArrayList<>();

    public Client() {}


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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public TypeClient getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(TypeClient typeClient) {
        this.typeClient = typeClient;
    }

    public Conseiller getConseiller() {
        return conseiller;
    }

    public void setConseiller(Conseiller conseiller) {
        this.conseiller = conseiller;
    }

    public CompteCourant getCompteCourant() {
        return compteCourant;
    }

    public void setCompteCourant(CompteCourant compteCourant) {
        this.compteCourant = compteCourant;
    }

    public CompteEpargne getCompteEpargne() {
        return compteEpargne;
    }

    public void setCompteEpargne(CompteEpargne compteEpargne) {
        this.compteEpargne = compteEpargne;
    }

    public List<Card> getCartes() {
        return cartes;
    }

    public void setCartes(List<Card> cartes) {
        this.cartes = cartes;
    }


    public void ajouterCarte(Card carte) {
        if (cartes.size() >= 2) {
            throw new IllegalStateException("Un client ne peut pas posséder plus de 2 cartes.");
        }
        carte.setClient(this);
        cartes.add(carte);
    }

    public void ajouterCompteCourant(CompteCourant compte) {
        this.compteCourant = compte;
    }

    public void ajouterCompteEpargne(CompteEpargne compte) {
        this.compteEpargne = compte;
    }
}
