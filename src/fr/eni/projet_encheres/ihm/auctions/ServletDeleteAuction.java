package fr.eni.projet_encheres.ihm.auctions;

import fr.eni.projet_encheres.bll.ArticleVenduManager;
import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.RetraitManager;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.ErrorsManagement;
import fr.eni.projet_encheres.ihm.ManagementTools.RequestManagement;
import fr.eni.projet_encheres.messages.MessageReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.eni.projet_encheres.ihm.ManagementTools.RequestManagement.processSeeAuctionPage;

@WebServlet("/deleteAuction/*")
public class ServletDeleteAuction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleVenduManager avm = new ArticleVenduManager();
        RetraitManager rm = new RetraitManager();
        int idAuctionToDelete = Integer.valueOf(request.getParameter("id"));
        List<String> errors = new ArrayList<>();
        try {
            ArticleVendu auctionToDelete = avm.getArticleById(idAuctionToDelete);
            // Check if there is tricky URL manipulation here
            // Is our user allowed to delete this auction ?
            int idUser = new UtilisateurManager().getUtilisateurByPseudo(request.getUserPrincipal().getName()).getNoUtilisateur();
            if (idUser != auctionToDelete.getNoUtilisateur()) {
                BLLException bllException = new BLLException();
                bllException.addError(40000);
                throw bllException;
            }
            rm.deleteRetrait(rm.getRetraitByNoArticle(idAuctionToDelete));
            avm.deleteArticle(auctionToDelete);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        if (errors.isEmpty()) {
            try {
                RequestManagement.processHomePageAttributes(request);
            } catch (DALException e) {
                ErrorsManagement.DALExceptionsCatcher(e, errors, request);
            } catch (BLLException e) {
                ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
            }
            request.setAttribute("auctionDeleted", "true");
            request.setAttribute("page", "home");
        } else {
            try {
                processSeeAuctionPage(request, idAuctionToDelete);
            } catch (DALException e) {
                ErrorsManagement.DALExceptionsCatcher(e, errors, request);
            } catch (BLLException e) {
                ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
            }
            request.setAttribute("page", "seeAuction");
        }
        rd.forward(request, response);
    }
}
