package eu.unareil.bo;

public class AuteurCartePostale {
    private long id;
    private long refAuteur;
    private long refCartePostale;

    public AuteurCartePostale(long refAuteur, long refCartePostale) {
        this.refAuteur = refAuteur;
        this.refCartePostale = refCartePostale;
    }

    public AuteurCartePostale(long id, long refAuteur, long refCartePostale) {
        this.id = id;
        this.refAuteur = refAuteur;
        this.refCartePostale = refCartePostale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRefAuteur() {
        return refAuteur;
    }

    public void setRefAuteur(long refAuteur) {
        this.refAuteur = refAuteur;
    }

    public long getRefCartePostale() {
        return refCartePostale;
    }

    public void setRefCartePostale(long refCartePostale) {
        this.refCartePostale = refCartePostale;
    }
}
