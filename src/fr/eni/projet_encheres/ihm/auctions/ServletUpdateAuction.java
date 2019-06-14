package fr.eni.projet_encheres.ihm.auctions;

import fr.eni.projet_encheres.bll.ArticleVenduManager;
import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.RetraitManager;
import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Retrait;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.ErrorsManagement;
import fr.eni.projet_encheres.ihm.ManagementTools.RequestManagement;

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
import java.util.List;

@WebServlet("/updateAuction/*")
public class ServletUpdateAuction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ArticleVenduManager avm = new ArticleVenduManager();
        RetraitManager rm = new RetraitManager();
        List<String> errors = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            int idAuctionToUpdate = Integer.valueOf(request.getParameter("id"));
            ArticleVendu auctionToUpdate = avm.getArticleById(idAuctionToUpdate);
            auctionToUpdate.setNomArticle(request.getParameter("name"));
            auctionToUpdate.setDescription(request.getParameter("description"));
            auctionToUpdate.setNoCategorie(Integer.valueOf(request.getParameter("category")));
            auctionToUpdate.setDateDebutEncheres(dateFormat.parse(request.getParameter("start_auction_date")));
            auctionToUpdate.setDateFinEncheres(dateFormat.parse(request.getParameter("end_auction_date")));
            auctionToUpdate.setPrixVente(Integer.valueOf(request.getParameter("starting_price")));
            auctionToUpdate.setPrixInitial(Integer.valueOf(request.getParameter("starting_price")));
            Retrait retraitToUpdate = rm.getRetraitByNoArticle(auctionToUpdate.getNoArticle());
            retraitToUpdate.setRue(request.getParameter("street"));
            retraitToUpdate.setCodePostal(request.getParameter("postal_code"));
            retraitToUpdate.setVille( request.getParameter("city"));
            avm.updateArticle(auctionToUpdate);
            rm.updateRetrait(retraitToUpdate);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        if (errors.isEmpty()) {
            try {
                RequestManagement.processHomePageAttributes(request);
            } catch (DALException e) {
                ErrorsManagement.DALExceptionsCatcher(e, errors, request);
            } catch (BLLException e) {
                ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
            }
            request.setAttribute("auctionUpdated", "true");
            request.setAttribute("page", "home");
        } else {
            RequestManagement.processUpdateAuctionPage(request, response);
            request.setAttribute("page", "updateAuction");
        }
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        int idAuctionToUpdate = Integer.valueOf(request.getParameter("id"));
        ArticleVenduManager avm = new ArticleVenduManager();
        RequestManagement.processUpdateAuctionPage(request, response);
        request.setAttribute("page", "updateAuction");
        rd.forward(request, response);
    }
}
