package fr.eni.projet_encheres.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
    private static DataSource dataSource;

    // Au chargement de la classe, la DataSource est recherchée dans l'arbre JNDI
    static
    {
        Context context;
        try {
            context = new InitialContext();
            ConnectionProvider.dataSource = (DataSource)context.lookup("java:comp/env/jdbc/pool_cnx");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible d'accéder à la base de données");
        }
    }
    
    /**
     * Cette méthode retourne une connexion opérationnelle issue du pool de connexion
     * vers la base de données. 
     * @return Connection
     * @throws SQLException If something went wrong with the context (not found, wrong values)
     */
    public static Connection getConnection() throws SQLException
    {
        return ConnectionProvider.dataSource.getConnection();
    }
}
