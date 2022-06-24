package eu.unareil.dal;

import eu.unareil.bo.Auteur;
import eu.unareil.dal.jdbc.AuteursJDBC;

public class DAOFactory {
    public static DAO<Auteur> getAuteursDAO(){
        DAO<Auteur> maDAO = new AuteursJDBC();
        return maDAO;
    }
}
