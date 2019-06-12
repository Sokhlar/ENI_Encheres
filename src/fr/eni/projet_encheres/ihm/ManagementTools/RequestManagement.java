package fr.eni.projet_encheres.ihm.ManagementTools;

import fr.eni.projet_encheres.bll.ArticleVenduManager;
import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.CategorieManager;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.dal.DALException;

import javax.servlet.http.HttpServletRequest;

public class RequestManagement {
    public static void processHomePageAttributes(HttpServletRequest request) throws DALException, BLLException {
        request.setAttribute("current_auctions", new ArticleVenduManager().getAllArticlesVendus());
        request.setAttribute("categories", new CategorieManager().getAllCategories());
        request.setAttribute("pseudos", new UtilisateurManager().getPseudosUtilisateursWithCurrentAuctions());
    }
}
