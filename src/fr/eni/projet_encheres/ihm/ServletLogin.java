package fr.eni.projet_encheres.ihm;

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
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
        if (request.getRequestURI().contains("error")) {
            request.setAttribute("page", "login");
            request.setAttribute("login_error", "true");
            rd.forward(request, response);
        } else if (request.isUserInRole("basic_user")) {
            setSessionConnected(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.isUserInRole("basic_user")) {
            setSessionConnected(request, response);
        } else {
            request.setAttribute("page", "login");
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/index.jsp");
            rd.forward(request, response);
        }
    }

    private void setSessionConnected (HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("isConnected", "true");
        response.sendRedirect(request.getContextPath());
    }
}
