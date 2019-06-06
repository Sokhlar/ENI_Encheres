package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.DAOEnchere;
import fr.eni.projet_encheres.dal.DAOFactory;

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
}
