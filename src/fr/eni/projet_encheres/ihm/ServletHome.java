package fr.eni.projet_encheres.ihm;

import fr.eni.projet_encheres.bll.*;
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
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        request.setAttribute("page", "home");
        List<String> errors = new ArrayList<>();
        ArticleVenduManager avm = new ArticleVenduManager();
        CategorieManager cm = new CategorieManager();
        UtilisateurManager um = new UtilisateurManager();
        EnchereManager em = new EnchereManager();
        String CURRENT_AUCTION_STATE = "EC";
        String NOT_STARTED_AUCTION_STATE = "PC";
        String FINISHED_AUCTION_STATE = "VE";

        request.setAttribute("current_auctions", null);
        try {
            Utilisateur utilisateurLogged = null;
            if (request.getUserPrincipal() != null) {
                utilisateurLogged = um.getUtilisateurByPseudo(request.getUserPrincipal().getName());
            }
            List<ArticleVendu> articlesVendus = avm.getAllArticlesVendus();
            String categoryFilter = request.getParameter("category_filter");
            // Let's filter !
            // By category
            // Create a clone of the ArrayList
            List<ArticleVendu> articlesToFilter = new ArrayList<>(articlesVendus);
            if (categoryFilter != null && !request.getParameter("category_filter").equals("all")) {
                // Loop into this clone and remove from the original list
                for (ArticleVendu articleVendu : articlesToFilter) {
                    if (articleVendu.getNoCategorie() != Integer.valueOf(categoryFilter)) {
                        articlesVendus.remove(articleVendu);
                    }
                }
            }
            // By keyword
            String stringFilter = request.getParameter("string_filter");
            // Actualize clone of the ArrayList
            articlesToFilter = new ArrayList<>(articlesVendus);
            if (stringFilter != null) {
                // Loop into this clone and remove from the original list
                for (ArticleVendu articleVendu : articlesToFilter) {
                    if (!articleVendu.getNomArticle().toLowerCase().contains(stringFilter.toLowerCase())) {
                        articlesVendus.remove(articleVendu);
                    }
                }
            }
            // Now the attributes for connected users
            if (request.getParameter("filterChoice") != null) {
                String filterChoice = request.getParameter("filterChoice");
                // Actualize clone of the ArrayList
                articlesToFilter = new ArrayList<>(articlesVendus);
                switch (filterChoice) {
                    case "currentAuctions":
                        // get what we want
                        List<Integer> currentAuctions = avm.getArticlesByEtat(CURRENT_AUCTION_STATE);
                        for(ArticleVendu articleVendu : articlesToFilter) {
                            // remove what we don't want
                            if (!currentAuctions.contains(articleVendu.getNoArticle())) {
                                articlesVendus.remove(articleVendu);
                            }
                        }
                        break;
                    case "myCurrentAuctions":
                        List<Integer> articlesToKeep = em.selectIdArticlesFromUserAndState(utilisateurLogged, CURRENT_AUCTION_STATE);
                        for (ArticleVendu articleVendu : articlesToFilter) {
                            if(!articlesToKeep.contains(articleVendu.getNoArticle())) {
                                articlesVendus.remove(articleVendu);
                            }
                        }
                        request.setAttribute("filterByMyCurrentAuctions", "true");
                        break;
                    case "myWonAuctions":
                        List<Integer> wonAuctions = em.selectIdArticlesWonByUser(utilisateurLogged);
                        for (ArticleVendu articleVendu : articlesToFilter) {
                            if (!wonAuctions.contains(articleVendu.getNoArticle())) {
                                articlesVendus.remove(articleVendu);
                            }
                        }
                        request.setAttribute("filterByWonAuctions", "true");
                        break;
                    case "myCurrentSales":
                        List<Integer> currentSales = avm.getArticlesFilteredBySellerAndState(utilisateurLogged, CURRENT_AUCTION_STATE);
                        for (ArticleVendu articleVendu : articlesToFilter) {
                            if (!currentSales.contains(articleVendu.getNoArticle())) {
                                articlesVendus.remove(articleVendu);
                            }
                        }
                        request.setAttribute("filterByMyCurrentSales", "true");
                        break;
                    case "myNotStartedSales":
                        List<Integer> notStartedSales = avm.getArticlesFilteredBySellerAndState(utilisateurLogged, NOT_STARTED_AUCTION_STATE);
                        for (ArticleVendu articleVendu : articlesToFilter) {
                            if (!notStartedSales.contains(articleVendu.getNoArticle())) {
                                articlesVendus.remove(articleVendu);
                            }
                        }
                        request.setAttribute("filterByMyNotStartedSales", "true");
                        break;
                    case "myEndedSales":
                        List<Integer> endedSales = avm.getArticlesFilteredBySellerAndState(utilisateurLogged, FINISHED_AUCTION_STATE);
                        for (ArticleVendu articleVendu : articlesToFilter) {
                            if (!endedSales.contains(articleVendu.getNoArticle())) {
                                articlesVendus.remove(articleVendu);
                            }
                        }
                        request.setAttribute("filterByMyEndedSales", "true");
                        break;
                }
            }

            request.setAttribute("current_auctions", articlesVendus);
            request.setAttribute("categories", cm.getAllCategories());
            request.setAttribute("pseudos", um.getPseudosUtilisateursWithCurrentAuctions());
            // We also have to keep the different filters in the way they were before the http request
            request.setAttribute("categoryFilter", categoryFilter);
            request.setAttribute("stringFilter", stringFilter);

        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        List<String> errors = new ArrayList<>();
        request.setAttribute("page", "home");
        CategorieManager cm = new CategorieManager();
        ArticleVenduManager avm = new ArticleVenduManager();
        UtilisateurManager um = new UtilisateurManager();
        try {
            request.setAttribute("current_auctions", avm.getAllArticlesVendus());
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
