package fr.eni.projet_encheres.ihm.users;

import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.ErrorsManagement;
import fr.eni.projet_encheres.ihm.ManagementTools.SessionManagement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/updateProfile")
public class ServletUpdateUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtilisateurManager um = new UtilisateurManager();
        request.setCharacterEncoding("UTF-8");
        List<String> errors = new ArrayList<>();
        try {
            Utilisateur utilisateurToUpdate = um.getUtilisateurByPseudo(request.getUserPrincipal().getName());
            utilisateurToUpdate.setPseudo(request.getParameter("pseudo"));
            utilisateurToUpdate.setNom(request.getParameter("name"));
            utilisateurToUpdate.setPrenom(request.getParameter("first_name"));
            utilisateurToUpdate.setEmail(request.getParameter("mail"));
            utilisateurToUpdate.setTelephone(request.getParameter("phone"));
            utilisateurToUpdate.setRue(request.getParameter("street"));
            utilisateurToUpdate.setCodePostal(request.getParameter("post_code"));
            utilisateurToUpdate.setVille(request.getParameter("city"));
            if (!request.getParameter("new_password").isEmpty()) {
                utilisateurToUpdate.setMotDePasse(request.getParameter("new_password"));
            }
            um.updateUtilisateur(utilisateurToUpdate);
            SessionManagement.setUtilisateurSessionBean(request, utilisateurToUpdate.getPseudo());
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors, request);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        if (errors.isEmpty()) {
            request.setAttribute("loginUpdated", "true");
            request.setAttribute("page", "home");
        } else {
            request.setAttribute("page", "updateProfile");
        }
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        request.setAttribute("page", "updateProfile");
        rd.forward(request, response);
    }
}
