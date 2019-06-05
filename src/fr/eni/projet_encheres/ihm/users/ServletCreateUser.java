package fr.eni.projet_encheres.ihm.users;

import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.ErrorsManagement;
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

@WebServlet("/createLogin")
public class ServletCreateUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = new Utilisateur(
                request.getParameter("pseudo"),
                request.getParameter("name"),
                request.getParameter("first_name"),
                request.getParameter("mail"),
                request.getParameter("phone"),
                request.getParameter("street"),
                request.getParameter("post_code"),
                request.getParameter("city"),
                request.getParameter("password"),
                0,
                false
        );
        List<String> errors = new ArrayList<>();
        UtilisateurManager um = new UtilisateurManager();
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        try {
            um.createUtilisateur(utilisateur);
        } catch (BLLException e) {
            ErrorsManagement.BLLExceptionsCatcher(e, errors);
        } catch (DALException e) {
            ErrorsManagement.DALExceptionsCatcher(e, errors, request);
        }
        if (errors.isEmpty()) {
            request.setAttribute("loginCreated", "true");
            request.setAttribute("page", "home");
        } else {
            request.setAttribute("page", "createLogin");
            request.setAttribute("errors", errors);
        }
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        request.setAttribute("page", "createLogin");
        rd.forward(request, response);
    }
}