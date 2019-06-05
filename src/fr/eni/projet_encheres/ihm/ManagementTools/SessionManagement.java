package fr.eni.projet_encheres.ihm.ManagementTools;

import fr.eni.projet_encheres.bll.UtilisateurManager;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManagement {
    /**
     * Get the session and record a session attribute
     * @param request The request instance
     */
    public static void setSessionConnected (HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("isConnected", "true");
    }

    /**
     * Destroy the session, bye bye user !
     * @param request The request instance
     */
    public static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    /**
     *Set a session JavaBean from the pseudo the connected user
     * @param request The request instance
     * @throws DALException If there were any SQL issue into the DAL
     */
    public static void setUtilisateurSessionBean(HttpServletRequest request) throws DALException {
        UtilisateurManager um = new UtilisateurManager();
        HttpSession session = request.getSession();
        String pseudo_utilisateur = request.getUserPrincipal().getName();

        Utilisateur utilisateurToBean = um.getUtilisateurByPseudo(pseudo_utilisateur);
        session.setAttribute("utilisateurSession", utilisateurToBean);
    }
}
