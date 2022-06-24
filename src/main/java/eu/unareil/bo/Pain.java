package eu.unareil.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pain extends ProduitPerrissable {
    private int poids;

    public Pain(long refProd, String marque, String libelle, int poids, long qteStock, float prixUnitaire) {
        super(refProd, LocalDate.now().plusDays(2), marque, libelle, qteStock, prixUnitaire);
        this.poids = poids;
    }

    public Pain(String marque, String libelle, int poids, long qteStock, float prixUnitaire) {
        super(LocalDate.now().plusDays(2), marque, libelle, qteStock, prixUnitaire);
        this.poids = poids;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return String.format("Pain [libelle=%s, marque=%s, prixUnitaire=%s euros,\n" +
            "qteStock=%s, dateLimiteConso=%s, poids=%sg]", getLibelle(), getMarque(), String.format("%.2f", getPrixUnitaire()).replace('.', ','), getQteStock(), getDateLimiteConso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), getPoids());
    }
}
