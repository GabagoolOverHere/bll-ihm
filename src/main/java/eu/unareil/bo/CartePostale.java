package eu.unareil.bo;

import java.util.List;

public class CartePostale extends Produit {
    List<Auteur> lesAuteurs;
    private String type;

    public CartePostale(long refProd, String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, TypeCartePostale type) {
        super(refProd, marque, libelle, qteStock, prixUnitaire);
        this.lesAuteurs = lesAuteurs;
        this.type = type.toString();
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, TypeCartePostale type) {
        super(marque, libelle, qteStock, prixUnitaire);
        this.lesAuteurs = lesAuteurs;
        this.type = type.toString();
    }

    public CartePostale(long refProd, String marque, String libelle, long qteStock, float prixUnitaire) {
        super(refProd, marque, libelle, qteStock, prixUnitaire);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Auteur> getLesAuteurs() {
        return lesAuteurs;
    }

    @Override
    public String toString() {
        int count = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CartePostale [libelle=%s, marque=%s, prixUnitaire=%s euros,\n" +
            "qteStock=%s, auteur(s)=", getLibelle(), getMarque(), String.format("%.2f", getPrixUnitaire()).replace('.', ','), getQteStock()));
        if (lesAuteurs != null) {
            for (Auteur a : lesAuteurs) {
                sb.append(String.format("auteur%d=%s, ", count, a.toString()));
                count++;
            }
        }
        sb.append(String.format("type=%s]", getType()));
        return sb.toString();
    }
}
