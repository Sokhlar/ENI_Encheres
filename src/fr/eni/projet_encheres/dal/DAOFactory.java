package fr.eni.projet_encheres.dal;

import fr.eni.projet_encheres.dal.jdbc.ArticleVenduDAOJdbcImpl;
import fr.eni.projet_encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAOFactory {
    public static DAOUtilisateur getDAOUtilisateur() {
        return new UtilisateurDAOJdbcImpl();
    }
    public static DAOArticleVendu getDAOArticleVendu() {
        return new ArticleVenduDAOJdbcImpl();
    }
}
