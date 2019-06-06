package fr.eni.projet_encheres.ihm.users;

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

@WebServlet("/deleteProfile")
public class ServletDeleteUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        UtilisateurManager um = new UtilisateurManager();
        try {
            Utilisateur userToDelete = um.getUtilisateurByPseudo(request.getUserPrincipal().getName());
            um.deleteUtilisateur(userToDelete);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        }
        if (errors.isEmpty()) {
            request.setAttribute("loginDeleted", "true");
            request.setAttribute("page", "home");
        } else {
            request.setAttribute("page", "updateProfile");
        }
        SessionManagement.destroySession(request);
        rd.forward(request, response);
    }
}
