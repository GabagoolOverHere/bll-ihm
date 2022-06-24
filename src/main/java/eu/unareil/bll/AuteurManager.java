package eu.unareil.bll;

import java.util.List;

import eu.unareil.bo.Auteur;
import eu.unareil.dal.DALException;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;


public class AuteurManager {
    private static volatile AuteurManager instance = null;
    private static DAO<Auteur> impl;

    private AuteurManager() {
        impl = DAOFactory.getAuteursDAO();
    }

    public final static AuteurManager getInstance() {
        if (AuteurManager.instance == null) {
            synchronized (AuteurManager.class) {
                if (AuteurManager.instance == null) {
                    AuteurManager.instance = new AuteurManager();
                }
            }
        }
        return AuteurManager.instance;
    }

    public List<Auteur> getLesElements() throws BLLException {
        List<Auteur> lesEle = null;
        try {
            lesEle = impl.selectAll();
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la récupération des éléments chimiques", e.getCause());
        }

        return lesEle;
    }

    public void ajouteElement(Auteur element) throws BLLException {
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

    public void supprimerElement(Auteur element) throws BLLException {
        try {
            valider(element);
            impl.delete(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la suppression de l'élément chimique " + element, e.getCause());

        }
    }

    public void modierElement(Auteur element) throws BLLException {
        valider(element);
        try {
            impl.update(element);
        } catch (DALException e) {
            // TODO Auto-generated catch block
            throw new BLLException("Erreur lors de la modification de l'élément chimique " + element, e.getCause());
        }

    }


    private void valider(Auteur el) throws BLLException {
        if (el == null)
            throw new BLLException("Auteur ne peut pas être null");

        if (!check(el.getNom()) || !check(el.getPrenom()))
            throw new BLLException("Nom et / ou prenom nom valides");

    }

    private boolean check(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars)
            if (!Character.isLetter(c))
                return false;

        return true;
    }


}
