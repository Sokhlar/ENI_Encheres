package fr.eni.projet_encheres.ihm.auctions;

import fr.eni.projet_encheres.bll.*;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Retrait;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.ErrorsManagement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/postAuction")
public class ServletPostAuction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        UtilisateurManager um = new UtilisateurManager();
        ArticleVenduManager avm = new ArticleVenduManager();
        RetraitManager rm = new RetraitManager();
        List<String> errors = new ArrayList<>();
        try {
            Utilisateur utilisateur = um.getUtilisateurByPseudo(request.getUserPrincipal().getName());
            // New auction
            ArticleVendu articleVendu = new ArticleVendu(
                request.getParameter("name"),
                    request.getParameter("description"),
                    dateFormat.parse(request.getParameter("start_auction_date")),
                    dateFormat.parse(request.getParameter("end_auction_date")),
                    Integer.valueOf(request.getParameter("starting_price")),
                    Integer.valueOf(request.getParameter("starting_price")),
                    "EC",
                    utilisateur.getNoUtilisateur(),
                    Integer.valueOf(request.getParameter("category"))
            );
            avm.createArticleVendu(articleVendu);
            // New retrait point
            Retrait retrait = new Retrait(
                    articleVendu.getNoArticle(),
                    request.getParameter("street"),
                    request.getParameter("postal_code"),
                    request.getParameter("city")
            );
            rm.createRetrait(retrait);
            request.setAttribute("current_auctions", avm.getArticlesByEtat("EC"));
            request.setAttribute("pseudos", um.getPseudosUtilisateursWithCurrentAuctions());
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        if (errors.isEmpty()) {
            request.setAttribute("page", "home");
            request.setAttribute("auctionCreated", "true");
        } else {
            CategorieManager cm = new CategorieManager();
            try {
                request.setAttribute("categories", cm.getAllCategories());
            } catch (DALException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            request.setAttribute("page", "postAuction");
        }
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        CategorieManager cm = new CategorieManager();
        try {
            request.setAttribute("categories", cm.getAllCategories());
        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.setAttribute("page", "postAuction");
        rd.forward(request, response);
    }
}
