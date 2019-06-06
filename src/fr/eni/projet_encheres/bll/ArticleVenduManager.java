package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.dao.DAOArticleVendu;
import fr.eni.projet_encheres.dal.dao.DAOFactory;

import java.util.Arrays;

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
        return bllException;
    }
}
