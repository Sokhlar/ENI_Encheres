package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.dao.DAOEnchere;
import fr.eni.projet_encheres.dal.dao.DAOFactory;

import java.util.List;

public class EnchereManager {
    public static DAOEnchere dao;

    static {
        dao = DAOFactory.getDAOEnchere();
    }

    /**
     * Create part of the CRUD
     * @param enchere The instance to add to the DB
     * @throws DALException If there is any issues with the DAL part
     */
    public void createEnchere(Enchere enchere) throws DALException {
        dao.insert(enchere);
    }

    public List<Integer> selectIdArticlesFromUserAndState(Utilisateur utilisateur, String state) throws DALException {
        return dao.getNoArticlesByUtilisateurAndEtat(utilisateur, state);
    }
}
