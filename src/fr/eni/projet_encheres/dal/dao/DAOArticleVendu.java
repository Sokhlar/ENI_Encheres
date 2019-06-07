package fr.eni.projet_encheres.dal.dao;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.dal.DALException;

import java.util.List;

public interface DAOArticleVendu extends DAO<ArticleVendu> {
    List<ArticleVendu> filterByCategory(Categorie categorie) throws DALException;
    List<ArticleVendu> filterByString(String filter) throws DALException;
    List<ArticleVendu> filterByEtat(String etat) throws DALException;
}
