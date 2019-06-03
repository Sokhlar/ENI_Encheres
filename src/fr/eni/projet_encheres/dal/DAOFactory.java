package fr.eni.projet_encheres.dal;

import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAOFactory {
    public static DAO<Utilisateur> getDAOUtilisateur() {
        return new UtilisateurDAOJdbcImpl();
    }
}
