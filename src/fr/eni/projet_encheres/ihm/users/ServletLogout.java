package fr.eni.projet_encheres.ihm.users;

import fr.eni.projet_encheres.ihm.SessionManagement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class ServletLogout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    // Destroy the session, so logout our user and redirects him to the home page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionManagement.destroySession(request);
        response.sendRedirect(request.getContextPath());
    }
}
