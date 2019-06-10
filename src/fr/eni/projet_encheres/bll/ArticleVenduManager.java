package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.dao.DAOArticleVendu;
import fr.eni.projet_encheres.dal.dao.DAOFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ArticleVenduManager {
    private static DAOArticleVendu dao;

    static {
        dao = DAOFactory.getDAOArticleVendu();
    }

    /**
     * Create part of CRUD
     * @param articleVendu The instance to add to the DB
     * @throws BLLException If there is any format issues with the instance
     * @throws DALException If there is any issues with the DAL part
     */
    public void createArticleVendu(ArticleVendu articleVendu) throws BLLException, DALException {
        BLLException bllException = validateArticleVendu(articleVendu);
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
            dao.insert(articleVendu);
        }
    }

    /**
     * Read part of CRUD
     * Select all from DB
     * @return An ArrayList filled with instances
     * @throws DALException if there any issues with the DAL part
     * @throws BLLException If the query returns no results
     */
    public List<ArticleVendu> getAllArticlesVendus() throws DALException, BLLException {
        List<ArticleVendu> articlesVendus = dao.selectAll();
        if (articlesVendus.isEmpty()) {
            BLLException bllException = new BLLException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw bllException;
        } else {
            return articlesVendus;
        }
    }
    /**
     * Select from the DB the articlesVendus from a particular state
     * @param etat the filter
     * @return An ArrayList filled with instances
     * @throws DALException if there any issues with the DAL part
     * @throws BLLException If the query returns no results
     */
    public List<Integer> getArticlesByEtat(String etat) throws DALException, BLLException {
        List<Integer> articlesVendus = dao.filterByEtat(etat);
        if (articlesVendus.isEmpty()) {
            BLLException bllException = new BLLException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw bllException;
        } else {
            return articlesVendus;
        }
    }

    /**
     * Select from the DB the articlesVendus from a particular category
     * @param categorie the filter
     * @return An ArrayList filled with instances
     * @throws DALException if there any issues with the DAL part
     * @throws BLLException If the query returns no results
     */
    public List<ArticleVendu> getArticlesFromCategory(Categorie categorie) throws DALException, BLLException {
        List<ArticleVendu> articlesVendus = dao.filterByCategory(categorie);
        if (articlesVendus.isEmpty()) {
            BLLException bllException = new BLLException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw bllException;
        } else {
            return articlesVendus;
        }
    }
    /**
     * Select from the DB the articlesVendus filtered by nomArticle
     * @param filter String the filter
     * @return An ArrayList filled with instances
     * @throws DALException if there any issues with the DAL part
     * @throws BLLException If the query returns no results
     */
    public List<ArticleVendu> getArticlesFilterByNomArticle(String filter) throws DALException, BLLException {
        List<ArticleVendu> articlesVendus = dao.filterByString(filter);
        if (articlesVendus.isEmpty()) {
            BLLException bllException = new BLLException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw bllException;
        } else {
            return articlesVendus;
        }
    }

    /**
     * Call the DAL for all articles id sell by a particular user in a particular state
     * @param utilisateur The user
     * @param state The State (can be EC, VA or AN)
     * @return An ArrayList filled with these instances
     * @throws DALException If there any issue with the DAL part
     * @throws BLLException If the query returns no results
     */
    public List<Integer> getArticlesFilteredBySellerAndState(Utilisateur utilisateur, String state) throws DALException, BLLException {
        List<Integer> articleVendus = dao.getArticlesFromASellerAndState(utilisateur, state);
        if (articleVendus.isEmpty()) {
            BLLException bllException = new BLLException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw bllException;
        } else {
            return articleVendus;
        }
    }

    /**
     * Validate the format or value of the different fields of ArticleVendu instance
     * before insert it into the DB
     * @param articleVendu The instance to validate
     * @return An instance of BLLException filled with the error codes that been raised
     */
    private BLLException validateArticleVendu(ArticleVendu articleVendu) {
        BLLException bllException = new BLLException();
        if (articleVendu.getNomArticle().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_NOM_ARTICLE);
        }
        if (articleVendu.getDescription().length() > 300) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_DESCRIPTION_ARTICLE);
        }
        String[] acceptedValuesEtatVente = {"EC", "AN", "VE"};
        if (!Arrays.asList(acceptedValuesEtatVente).contains(articleVendu.getEtatVente())) {
            bllException.addError(ErrorCodesBLL.ERROR_VALUE_STATUT_VENTE_ARTICLE);
        }
        if (articleVendu.getDateDebutEncheres().after(articleVendu.getDateFinEncheres())) {
            bllException.addError(ErrorCodesBLL.ERROR_START_DATE_AFTER_END_DATE);
        }
        if (articleVendu.getDateDebutEncheres().before(new Date()) || articleVendu.getDateFinEncheres().before(new Date())) {
            bllException.addError(ErrorCodesBLL.ERROR_DATE_BEFORE_TODAY);
        }
        return bllException;
    }
}
