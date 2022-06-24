package eu.unareil.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Glace extends ProduitPerrissable {
    private String parfum;
    private int temperatureConservation;

    public Glace(long refProd, LocalDate dateLimiteConso, String marque, String libelle, long qteStock, float prixUnitaire, String parfum, int temperatureConservation) {
        super(refProd, dateLimiteConso, marque, libelle, qteStock, prixUnitaire);
        this.parfum = parfum;
        this.temperatureConservation = temperatureConservation;
    }

    public Glace(LocalDate dateLimiteConso, String marque, String libelle, int temperatureConservation, String parfum, long qteStock, float prixUnitaire) {
        super(dateLimiteConso, marque, libelle, qteStock, prixUnitaire);
        this.parfum = parfum;
        this.temperatureConservation = temperatureConservation;
    }

    public String getParfum() {
        return parfum;
    }

    public void setParfum(String parfum) {
        this.parfum = parfum;
    }

    public int getTemperatureConservation() {
        return temperatureConservation;
    }

    public void setTemperatureConservation(int temperatureConservation) {
        this.temperatureConservation = temperatureConservation;
    }

    @Override
    public String toString() {
        return String.format("Glace [libelle=%s, marque=%s, prixUnitaire=%s euros,\n" +
            "qteStock=%s, dateLimiteConso=%s, parfum=%s, temperatureConservation=%s", getLibelle(), getMarque(), String.format("%.2f", getPrixUnitaire()).replace('.', ','), getQteStock(), getDateLimiteConso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), getParfum(), getTemperatureConservation());
    }

}
