package fr.eni.projet_encheres.ihm.auctions;

import fr.eni.projet_encheres.bll.*;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.ErrorsManagement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/auction/*")
public class ServletSeeAuction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        int idAuction = Integer.valueOf(request.getParameter("id"));
        ArticleVenduManager avm = new ArticleVenduManager();
        CategorieManager cm = new CategorieManager();
        EnchereManager em = new EnchereManager();
        RetraitManager rm = new RetraitManager();
        UtilisateurManager um = new UtilisateurManager();
        List<String> errors = new ArrayList<>();
        try {
            ArticleVendu articleVendu = avm.getArticleById(idAuction);
            request.setAttribute("auction", articleVendu);
            request.setAttribute("category", cm.getCategorieById(articleVendu.getNoCategorie()));
            if (em.getAmountAndPseudoOfBestOffer(articleVendu) != null) {
                HashMap<Integer, Integer> bestOffer = em.getAmountAndPseudoOfBestOffer(articleVendu);
                request.setAttribute("user_best_offer", um.getUtilisateurById(bestOffer.get(articleVendu.getPrixVente())).getPseudo());
            }
            request.setAttribute("withdrawal", rm.getRetraitByNoArticle(articleVendu.getNoArticle()));
            request.setAttribute("seller", um.getUtilisateurById(articleVendu.getNoUtilisateur()));
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        request.setAttribute("page", "seeAuction");
        rd.forward(request, response);
    }
}
