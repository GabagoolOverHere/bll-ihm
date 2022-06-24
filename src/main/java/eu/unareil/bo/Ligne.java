package eu.unareil.bo;

public class Ligne {
    private int quantite;
    private Produit produit;

    public Ligne() {
    }

    public Ligne(Produit produit, int quantite) {
        this.quantite = quantite;
        this.produit = produit;
    }

    public int getQte() {
        return quantite;
    }

    public void setQte(int quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getPrix() {
        return this.produit.getPrixUnitaire() * this.quantite;
    }

    @Override
    public String toString() {
        return String.format("Ligne [produit=%s, qte=%s, prix=%s euros]", produit.toString(), getQte(), String.format("%.2f", getPrix()).replace('.', ','));
    }
}
