package fr.eni.projet_encheres.ihm.auctions;

import fr.eni.projet_encheres.bll.ArticleVenduManager;
import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.EnchereManager;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Enchere;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/raiseAuction")
public class ServletRaiseAuction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int noArticle = Integer.valueOf(request.getParameter("id"));
        List<String> errors = new ArrayList<>();
        ArticleVenduManager avm = new ArticleVenduManager();
        EnchereManager em = new EnchereManager();
        UtilisateurManager um = new UtilisateurManager();
        try {
            // The new auction
            ArticleVendu auctionToRaise = avm.getArticleById(noArticle);
            Utilisateur newAuctionUser = um.getUtilisateurByPseudo(request.getUserPrincipal().getName());
            int idUser = newAuctionUser.getNoUtilisateur();
            int newOffer = Integer.valueOf(request.getParameter("myAuction"));
            Enchere newAuction = new Enchere(
                idUser,
                noArticle,
                new Date(),
                newOffer
            );
            // Was there already an auction on this ?
            HashMap<Integer, Integer> previousBestAuction = em.getAmountAndPseudoOfBestOffer(auctionToRaise);
            if (previousBestAuction != null) {
                // Get the last best auction
                int previousOffer = auctionToRaise.getPrixVente();
                // Get the user who made it
                int previousAuctionUserId = previousBestAuction.get(previousOffer);
                Utilisateur previousAuctionUser = um.getUtilisateurById(previousAuctionUserId);
                // And refill him
                um.updateCredit(previousAuctionUserId, previousAuctionUser.getCredit() + previousOffer);
            }
            //Create the new auction
            em.createEnchere(newAuction);
            // And credit the user who made it
            um.updateCredit(idUser, newAuctionUser.getCredit() - newOffer);
            // Set the new price
            avm.updateCurrentPrice(noArticle, newOffer);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        // Now go back to the auction page
        response.sendRedirect(request.getContextPath() + "/auction?id=" + noArticle);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
