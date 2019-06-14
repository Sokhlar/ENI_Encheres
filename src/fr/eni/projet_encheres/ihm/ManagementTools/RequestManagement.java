package fr.eni.projet_encheres.ihm.ManagementTools;

import fr.eni.projet_encheres.bll.ArticleVenduManager;
import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.CategorieManager;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.dal.DALException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestManagement {
    public static void processHomePageAttributes(HttpServletRequest request) throws DALException, BLLException {
        request.setAttribute("current_auctions", new ArticleVenduManager().getAllArticlesVendus());
        request.setAttribute("categories", new CategorieManager().getAllCategories());
        request.setAttribute("pseudos", new UtilisateurManager().getPseudosUtilisateursWithCurrentAuctions());
    }
    public static void processInsertOrUpdateAuctionPageAttributes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CategorieManager cm = new CategorieManager();
            request.setAttribute("categories", cm.getAllCategories());
        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static void processUpdateAuctionPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idAuctionToUpdate = Integer.valueOf(request.getParameter("id"));
        ArticleVenduManager avm = new ArticleVenduManager();
        List<String> errors = new ArrayList<>();
        try {
            ArticleVendu auctionToUpdate = avm.getArticleById(idAuctionToUpdate);
            request.setAttribute("auctionToUpdate", auctionToUpdate);
            RequestManagement.processInsertOrUpdateAuctionPageAttributes(request, response);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
    }
}
