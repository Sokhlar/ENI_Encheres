package fr.eni.projet_encheres.dal;

import fr.eni.projet_encheres.bo.Retrait;
import fr.eni.projet_encheres.dal.jdbc.*;

public class DAOFactory {
    public static DAOUtilisateur getDAOUtilisateur() {
        return new UtilisateurDAOJdbcImpl();
    }
    public static DAOArticleVendu getDAOArticleVendu() {
        return new ArticleVenduDAOJdbcImpl();
    }
    public static DAOCategorie getDAOCategorie() {
        return new CategorieDAOJdbcImpl();
    }
    public static DAOEnchere getDAOEnchere() {
        return new EnchereDAOJdbcImpl();
    }
    public static DAO<Retrait> getDAORetrait() {
        return new RetraitDAOJdbcImpl();
    }
}
