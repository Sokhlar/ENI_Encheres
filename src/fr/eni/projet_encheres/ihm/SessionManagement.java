package fr.eni.projet_encheres.ihm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

class SessionManagement {
    /**
     * Get the session and record a session attribute
     * @param request The request instance
     */
    static void  setSessionConnected (HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("isConnected", "true");
    }

    /**
     * Destroy the session, bye bye user !
     * @param request The request instance
     */
    static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
