package fr.eni.projet_encheres.dal.dao;

import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.dal.DALException;

public interface DAOCategorie extends DAO<Categorie> {
    boolean checkForUniqueCategorieLibelle(String libelleToCheck) throws DALException;
}
