package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.DAOCategorie;
import fr.eni.projet_encheres.dal.DAOFactory;

import java.util.List;

public class CategorieManager {
    private static DAOCategorie dao;

    static {
        dao = DAOFactory.getDAOCategorie();
    }
    /**
     * Create part of CRUD
     * @param categorie The instance to add to the DB
     * @throws BLLException If there is any format issues with the instance
     * @throws DALException If there is any issues with the DAL part
     */
    public void createCategorie(Categorie categorie) throws DALException, BLLException {
        BLLException bllException = new BLLException();
        if (categorie.getLibelle().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_LIBELLE_CATEGORIE);
        }
        if (!dao.checkForUniqueCategorieLibelle(categorie.getLibelle())) {
            bllException.addError(ErrorCodesBLL.ERROR_LIBELLE_CATEGORIE_ALREADY_TAKEN);
        }
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
            dao.insert(categorie);
        }
    }
    /**
     * Read part of CRUD
     * @param id the primary key of the categorie to get
     * @return The instance of Categorie that matched the id param
     * @throws DALException If there is any issues with the DAL part
     */
    public Categorie getCategorieById(int id) throws DALException {
        return dao.selectById(id);
    }
    /**
     * Read part of CRUD
     * @return An ArrayList of instances of every Categories in the database
     * @throws DALException If there is any issues with the DAL part
     */
    public List<Categorie> getAllCategories() throws DALException {
        return dao.selectAll();
    }
}
