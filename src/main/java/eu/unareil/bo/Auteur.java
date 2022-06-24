package eu.unareil.bo;

import java.util.List;

public class Auteur {
    private long id;
    private String nom;
    private String prenom;
    private List<CartePostale> cartePostaleList;

    public Auteur(String prenom, String nom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Auteur(long id, String nom, String prenom, List<CartePostale> cartePostaleList) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cartePostaleList = cartePostaleList;
    }

    public Auteur(long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public List<CartePostale> getCartePostaleList() {
        return cartePostaleList;
    }

    public void setCartePostaleList(List<CartePostale> cartePostaleList) {
        this.cartePostaleList = cartePostaleList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Auteur{" +
            "id=" + id +
            ", nom='" + nom + '\'' +
            ", prenom='" + prenom + '\'' +
            ", cartePostaleList=" + cartePostaleList +
            '}';
    }
}
