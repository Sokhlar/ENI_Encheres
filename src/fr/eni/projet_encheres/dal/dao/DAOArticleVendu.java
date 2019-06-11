package fr.eni.projet_encheres.dal.dao;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;

import java.util.List;

public interface DAOArticleVendu extends DAO<ArticleVendu> {
    List<ArticleVendu> filterByCategory(Categorie categorie) throws DALException;
    List<ArticleVendu> filterByString(String filter) throws DALException;
    List<Integer> filterByEtat(String etat) throws DALException;
    List<Integer> getArticlesFromASellerAndState (Utilisateur utilisateur, String state) throws DALException;
    void updateCurrentPrice(int noArticle, int newPrice) throws DALException;
}
