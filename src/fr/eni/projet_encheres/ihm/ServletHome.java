package fr.eni.projet_encheres.ihm;

import fr.eni.projet_encheres.bll.ArticleVenduManager;
import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.CategorieManager;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.ArticleVendu;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class ServletHome extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        List<String> errors = new ArrayList<>();
        request.setAttribute("page", "home");
        CategorieManager cm = new CategorieManager();
        ArticleVenduManager avm = new ArticleVenduManager();
        UtilisateurManager um = new UtilisateurManager();
        try {
            request.setAttribute("current_auctions", avm.getArticlesByEtat("EC"));
            request.setAttribute("categories", cm.getAllCategories());
            request.setAttribute("pseudos", um.getPseudosUtilisateursWithCurrentAuctions());
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        rd.forward(request, response);
    }
}
