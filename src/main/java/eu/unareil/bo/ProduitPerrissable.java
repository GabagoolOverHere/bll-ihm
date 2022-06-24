package eu.unareil.bo;

import java.time.LocalDate;

abstract public class ProduitPerrissable extends Produit {
    private LocalDate dateLimiteConso;

    protected ProduitPerrissable(long refProd, LocalDate dateLimiteConso, String marque, String libelle, long qteStock, float prixUnitaire) {
        super(refProd, marque, libelle, qteStock, prixUnitaire);
        this.dateLimiteConso = dateLimiteConso;
    }

    protected ProduitPerrissable(LocalDate dateLimiteConso, String marque, String libelle, long qteStock, float prixUnitaire) {
        super(marque, libelle, qteStock, prixUnitaire);
        this.dateLimiteConso = dateLimiteConso;
    }

    public LocalDate getDateLimiteConso() {
        return dateLimiteConso;
    }

    public void setDateLimiteConso(LocalDate dateLimiteConso) {
        this.dateLimiteConso = dateLimiteConso;
    }

    @Override
    public String toString() {
        return "ProduitPerrissable{" +
            "dateLimiteConso=" + dateLimiteConso +
            '}';
    }
}
