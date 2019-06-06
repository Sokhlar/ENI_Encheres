package fr.eni.projet_encheres.dal;

import fr.eni.projet_encheres.bo.Categorie;

public interface DAOCategorie extends DAO<Categorie> {
    boolean checkForUniqueCategorieLibelle(String libelleToCheck) throws DALException;
}
