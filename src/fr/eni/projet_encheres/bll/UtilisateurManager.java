package fr.eni.projet_encheres.bll;

import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.DAO;
import fr.eni.projet_encheres.dal.DAOFactory;

import java.util.List;
import java.util.regex.Pattern;

public class UtilisateurManager {
    private static DAO<Utilisateur> dao;

    static {
        dao = DAOFactory.getDAOUtilisateur();
    }

    /**
     * Create part of CRUD
     * @param utilisateur The instance to add to the DB
     * @throws BLLException If there is any format issues with the instance
     * @throws DALException If there is any issues with the DAL part
     */
    public void createUtilisateur(Utilisateur utilisateur) throws BLLException, DALException {
        BLLException bllException = validateUtilisateur(utilisateur);
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
            dao.insert(utilisateur);
        }
    }

    /**
     * Read part of CRUD
     * @param id the primary key of the utilisateur to get
     * @return The instance of Utilisateur that matched the id param
     * @throws DALException If there is any issues with the DAL part
     */
    public Utilisateur getUtilisateurById(int id) throws DALException {
        return dao.selectById(id);
    }
    /**
     * Read part of CRUD
     * @return An ArrayList of instances of every Utilisateurs in the database
     * @throws DALException If there is any issues with the DAL part
     */
    public List<Utilisateur> getAllUtilisateurs() throws DALException {
        return dao.selectAll();
    }
    /**
     * Update part of CRUD
     * @param utilisateur The instance to update to the DB
     *                    (must have the no_utilisateur filled and matched or DALException will be thrown)
     * @throws BLLException If there is any format issues with the instance
     * @throws DALException If there is any issues with the DAL part
     */
    public void updateUtilisateur (Utilisateur utilisateur) throws BLLException, DALException {
        BLLException bllException = validateUtilisateur(utilisateur);
        if (bllException.hasErrors()) {
            throw bllException;
        } else {
            dao.update(utilisateur);
        }
    }

    /**
     * Delete part of CRUD
     * @param utilisateur The instance to delete from the DB
     *                    (must have the no_utilisateur filled and matched or DALException will be thrown)
     * @throws DALException If there is any issues with the DAL part
     */
    public void deleteUtilisateur (Utilisateur utilisateur) throws DALException {
        dao.delete(utilisateur);
    }

    /**
     * Validate the format of different fields from an instance of Utilisateurs before any actions with the DB
     * Use regexp for email and phone number
     * Check length of the different fields
     * @param utilisateur The instance to validate
     * @return An instance of BLLException filled with the error codes that been raised
     */
    private BLLException validateUtilisateur(Utilisateur utilisateur) {
        // This regexp is made from the RFC 5322 : http://www.ietf.org/rfc/rfc5322.txt
        // and has been taken from here : https://emailregex.com/
        String emailValidationRegEx = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
        // This regexp is made for matching only french telephone numbers.
        // format : 0101010101, no space and no special characters
        String telephoneNumberValidationRegEx = "^0[1-9][0-9]{8}$";
        BLLException bllException = new BLLException();

        if (utilisateur.getPseudo().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_PSEUDO_UTILISATEUR);
        }
        if (utilisateur.getNom().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_NOM_UTILISATEUR);
        }
        if (utilisateur.getPrenom().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_PRENOM_UTILISATEUR);
        }
        if (utilisateur.getEmail().length() > 40) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_EMAIL_UTILISATEUR);
        }
        if (!Pattern.matches(emailValidationRegEx, utilisateur.getEmail())) {
            bllException.addError(ErrorCodesBLL.ERROR_FORMAT_EMAIL_UTILISATEUR);
        }
        if (utilisateur.getTelephone().length() > 15) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_TELEPHONE_UTILISATEUR);
        }
        if (!Pattern.matches(telephoneNumberValidationRegEx, utilisateur.getTelephone())) {
            bllException.addError(ErrorCodesBLL.ERROR_FORMAT_TELEPHONE_UTILISATEUR);
        }
        if (utilisateur.getRue().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_RUE_UTILISATEUR);
        }
        if (utilisateur.getCodePostal().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_CODE_POSTAL_UTILISATEUR);
        }
        if (utilisateur.getVille().length() > 30) {
            bllException.addError(ErrorCodesBLL.ERROR_LENGTH_VILLE_UTILISATEUR);
        }

        return bllException;
    }
}
