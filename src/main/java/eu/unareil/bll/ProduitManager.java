package eu.unareil.bll;

import eu.unareil.bo.Produit;
import eu.unareil.bo.ProduitPerrissable;
import eu.unareil.dal.DALException;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;

import java.time.LocalDate;
import java.util.List;

public class ProduitManager {
    private static volatile ProduitManager instance = null;
    private static DAO<Produit> impl;

    private ProduitManager() {
        impl = DAOFactory.getProduitsDAO();
    }

    public final static ProduitManager getInstance() {
        if (ProduitManager.instance == null) {
            synchronized (ProduitManager.class) {
                if (ProduitManager.instance == null) {
                    ProduitManager.instance = new ProduitManager();
                }
            }
        }
        return ProduitManager.instance;
    }

    public List<Produit> getLesElements() throws BLLException {
        List<Produit> lesEle = null;
        try {
            lesEle = impl.selectAll();
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la récupération des éléments chimiques", e.getCause());
        }

        return lesEle;
    }

    public void ajouteElement(Produit element) throws BLLException {
        if (element.getRefProd() != 0) {
            throw new BLLException("Element chimique déjà existant");
        }
        valider(element);
        try {
            impl.insert(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de l'ajout de l'élément chimique " + element, e.getCause());
        }

    }

    public void supprimerElement(Produit element) throws BLLException {
        try {
            valider(element);
            impl.delete(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la suppression de l'élément chimique " + element, e.getCause());

        }
    }

    public void modierElement(Produit element) throws BLLException {
        valider(element);
        try {
            impl.update(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la modification de l'élément chimique " + element, e.getCause());
        }

    }


    private void valider(Produit el) throws BLLException {
        if (el == null)
            throw new BLLException("Produit ne peut pas être null");

        if (el.getPrixUnitaire() == 0)
            throw new BLLException("Le prix d'un produit ne peut etre nul.");

        if (el instanceof ProduitPerrissable && ((ProduitPerrissable) el).getDateLimiteConso().isBefore(LocalDate.now()))
            throw new BLLException("La date d'expiration ne peut pas etre inferieur a la date courante.");

    }
}
