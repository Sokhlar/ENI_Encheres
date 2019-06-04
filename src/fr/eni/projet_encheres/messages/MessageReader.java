package fr.eni.projet_encheres.messages;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Read from .properties files
 */
public abstract class MessageReader {
    private static Properties props;

    static {
        try {
            InputStream utf8in = MessageReader.class.getClassLoader().getResourceAsStream("fr/eni/projet_encheres/messages/error_messages.properties");
            Reader reader = new InputStreamReader(utf8in, StandardCharsets.UTF_8);
            props = new Properties();
            props.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the messages from codes in the file
     * @param code int The code to get the message from
     * @return The message or an error message if it could not
     */
    public static  String getMessageReader(int code)
    {
        String message;
        try {
            if(props != null) {
                message = props.getProperty(String.valueOf(code));
            } else {
                message="Problème à la lecture du fichier";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Une erreur inconnue est survenue";
        }
        return message;
    }
}
