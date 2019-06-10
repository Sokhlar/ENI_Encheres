package fr.eni.projet_encheres.dal.dao;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;

import java.util.List;

public interface DAOEnchere extends DAO<Enchere> {
    public List<Integer> getNoArticlesByUtilisateurAndEtat(Utilisateur utilisateur, String state) throws DALException;
}
