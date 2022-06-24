package eu.unareil.dal;

import eu.unareil.bo.Auteur;
import eu.unareil.bo.AuteurCartePostale;
import eu.unareil.bo.Produit;
import eu.unareil.dal.jdbc.AuteurCartePostaleJDBC;
import eu.unareil.dal.jdbc.AuteursJDBC;
import eu.unareil.dal.jdbc.ProduitJDBC;

public class DAOFactory {
    public static DAO<Auteur> getAuteursDAO() {
        DAO<Auteur> maDAO = new AuteursJDBC();
        return maDAO;
    }

    public static DAO<Produit> getProduitsDAO() {
        DAO<Produit> maDAO = new ProduitJDBC();
        return maDAO;
    }

    public static DAO<AuteurCartePostale> getAuteurCartePostalesDAO() {
        DAO<AuteurCartePostale> maDAO = new AuteurCartePostaleJDBC();
        return maDAO;
    }
}
