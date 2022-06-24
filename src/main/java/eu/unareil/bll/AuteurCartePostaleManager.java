package eu.unareil.bll;

import eu.unareil.bo.AuteurCartePostale;
import eu.unareil.dal.DALException;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;

import java.util.List;

public class AuteurCartePostaleManager {
    private static volatile AuteurCartePostaleManager instance = null;
    private static DAO<AuteurCartePostale> impl;

    private AuteurCartePostaleManager() {
        impl = DAOFactory.getAuteurCartePostalesDAO();
    }

    public final static AuteurCartePostaleManager getInstance() {
        if (AuteurCartePostaleManager.instance == null) {
            synchronized (AuteurCartePostaleManager.class) {
                if (AuteurCartePostaleManager.instance == null) {
                    AuteurCartePostaleManager.instance = new AuteurCartePostaleManager();
                }
            }
        }
        return AuteurCartePostaleManager.instance;
    }

    public List<AuteurCartePostale> getLesElements() throws BLLException {
        List<AuteurCartePostale> lesEle = null;
        try {
            lesEle = impl.selectAll();
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la récupération des éléments chimiques", e.getCause());
        }

        return lesEle;
    }

    public void ajouteElement(AuteurCartePostale element) throws BLLException {
        if (element.getId() != 0) {
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

    public void supprimerElement(AuteurCartePostale element) throws BLLException {
        try {
            valider(element);
            impl.delete(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la suppression de l'élément chimique " + element, e.getCause());

        }
    }

    public void modierElement(AuteurCartePostale element) throws BLLException {
        valider(element);
        try {
            impl.update(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la modification de l'élément chimique " + element, e.getCause());
        }

    }


    private void valider(AuteurCartePostale el) throws BLLException {
        if (el == null)
            throw new BLLException("AuteurCartePostale ne peut pas être null");

    }
}
