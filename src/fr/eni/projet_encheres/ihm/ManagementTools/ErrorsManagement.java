package fr.eni.projet_encheres.ihm.ManagementTools;

import fr.eni.projet_encheres.bll.BLLException;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.messages.MessageReader;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ErrorsManagement {
    /**
     * Treat a BLLException errors message and them to an ArrayList
     * @param e The BLLException
     * @param errors The ArrayList
     * @param request The instance of the HTTP request
     */
    public static void BLLExceptionsCatcher(BLLException e, List<String> errors, HttpServletRequest request) {
        for (Integer code_error : e.getListErrorCodes()) {
            errors.add(MessageReader.getMessageReader(code_error));
        }
        request.setAttribute("errors", errors);
    }
    /**
     * Treat a BLLException errors message and them to an ArrayList
     * Send also a custom attribute to the jsp
     * @param e The BLLException
     * @param errors The ArrayList
     * @param request The instance of the HTTP request
     */
    public static void DALExceptionsCatcher(DALException e, List<String> errors, HttpServletRequest request) {
        request.setAttribute("error_name", "Erreur avec la base de données : ");
        for (Integer code_error : e.getListErrorCodes()) {
            errors.add(MessageReader.getMessageReader(code_error));
        }
        request.setAttribute("errors", errors);
    }
}
