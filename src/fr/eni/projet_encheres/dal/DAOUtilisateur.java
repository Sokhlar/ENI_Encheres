package fr.eni.projet_encheres.dal;

import fr.eni.projet_encheres.bo.Utilisateur;

public interface DAOUtilisateur extends DAO<Utilisateur> {
    boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException;
    Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException;
}
