package fr.eni.projet_encheres.dal.dao;

import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;

public interface DAOUtilisateur extends DAO<Utilisateur> {
    boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException;
    Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException;
}
