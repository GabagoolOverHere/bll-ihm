package eu.unareil.bo;

import java.util.ArrayList;
import java.util.List;

public class Achat {
    private double montant;
    private List<Ligne> lignes = new ArrayList<>();

    public Achat() {
    }

    public Achat(Ligne ligne) {
        this.lignes.add(ligne);
    }

    public Ligne getLigne(int index) {
        return lignes.get(index);
    }

    public List<Ligne> getLignes() {
        return lignes;
    }

    public void ajouteLigne(Produit p, int qte) {
        lignes.add(new Ligne(p, qte));
    }

    public void modifieLigne(int index, int nouvelleQte) {
        lignes.set(index, new Ligne(lignes.get(index).getProduit(), nouvelleQte));
    }

    public void supprimeLigne(int index) {
        lignes.remove(index);
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double calculMontant() {
        double total = 0;
        for (Ligne l : lignes)
            total += l.getPrix();

        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (Ligne l : lignes) {
            sb.append(String.format("ligne %d :       %s\n", count, l.toString()));
            count++;
        }
        sb.append("\n");
        sb.append(String.format("Total de l'achat %.2f euros", calculMontant()).replace('.', ','));

        return sb.toString();
    }
}
