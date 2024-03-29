package fr.eni.projet_encheres.ihm.users;

import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.ihm.ManagementTools.SessionManagement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/login_error", "/j_security_check"})
public class ServletLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tomcat do the authentication
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        if (request.getRequestURI().contains("error")) {
            // display errors
            request.setAttribute("page", "login");
            request.setAttribute("login_error", "true");
            rd.forward(request, response);
        } else if (request.isUserInRole("basic_user")) {
            // authentication sucess !
            SessionManagement.setSessionConnected(request);
            try {
                // Set the user informations in the session in order to display them everywhere
                SessionManagement.setUtilisateurSessionBean(request, request.getUserPrincipal().getName());
            } catch (DALException e) {
                // This is serious
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.sendRedirect(request.getContextPath());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.isUserInRole("basic_user")) {
            SessionManagement.setSessionConnected(request);
            try {
                // Set the user informations in the session in order to display them everywhere
                SessionManagement.setUtilisateurSessionBean(request, request.getUserPrincipal().getName());
            } catch (DALException e) {
                // This is serious
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            HttpSession session = request.getSession();
            // If there is this session attribute, we redirect to it
            if (session.getAttribute("uriAndParamsRequested") != null) {
                response.sendRedirect((String) session.getAttribute("uriAndParamsRequested"));
            } else {
                response.sendRedirect(request.getContextPath());
            }

        } else {
            // 1st call to this servlet : GET request without any session bean set
            request.setAttribute("page", "login");
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
            rd.forward(request, response);
        }
    }
}
