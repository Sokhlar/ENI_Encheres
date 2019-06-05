package fr.eni.projet_encheres.ihm.users;

import fr.eni.projet_encheres.bll.UtilisateurManager;
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

@WebServlet("/user/*")
public class ServletSeeUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        request.setAttribute("page", "profile");
        UtilisateurManager um = new UtilisateurManager();
        List<String> errors = new ArrayList<>();
        try {
            Utilisateur utilisateur = um.getUtilisateurById(Integer.valueOf(request.getParameter("id")));
            request.setAttribute("utilisateurRequest", utilisateur);
            // If the ids match, user can update it
            if (utilisateur.getNoUtilisateur() == Integer.valueOf(request.getParameter("id"))) {
                request.setAttribute("canUpdate", "true");
            }
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        }
        rd.forward(request, response);
    }
}
