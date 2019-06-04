package fr.eni.projet_encheres.dal.jdbc;

import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.DAO;
import fr.eni.projet_encheres.dal.ErrorCodesDAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOJdbcImpl implements DAO<Utilisateur> {

    /**
     * Insert an instance into the DB and fill this instance with the id generated by mssql server
     * @param  utilisateur Utilisateur The instance to insert into the DB
     * @throws DALException if the SQL INSERT request is wrong
     */
    @Override
    public void insert(Utilisateur utilisateur) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String INSERT = "INSERT INTO UTILISATEURS " +
                    "(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            fillPreparedStatement(utilisateur, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setNoUtilisateur(rs.getInt(1));
            }
            setSecurityRoles(utilisateur);
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_INSERT);
            throw dalException;
        }
    }

    /**
     * Extract data from the DB by id
     * @param id int The id of the utilisateur to extract from the DB
     * @return utilisateur An instance of the utilisateur
     * @throws DALException if the SQL INSERT request is wrong
     */
    @Override
    public Utilisateur selectById(int id) throws DALException {
        Connection cnx = JdbcTools.connect();
        Utilisateur utilisateur = null;
        try {
            String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                utilisateur = hydrateUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return utilisateur;
    }
    /**
     * Select all the utilisateurs from the DB
     * @return An ArrayList filled with instances of Utilisateur
     */
    @Override
    public List<Utilisateur> selectAll() throws DALException {
        Connection cnx = JdbcTools.connect();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            Statement stmt = cnx.createStatement();
            String SELECT_ALL = "SELECT * FROM UTILISATEURS";
            stmt.execute(SELECT_ALL);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                utilisateurs.add(hydrateUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return utilisateurs;
    }
    /**
     * Update to the db from an instance of Utilisateur
     * @param utilisateur Utilisateur the instance filled with informations to update
     */
    @Override
    public void update(Utilisateur utilisateur) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String UPDATE = "UPDATE UTILISATEURS SET " +
                    "                        pseudo = ?, " +
                    "                        nom = ?, " +
                    "                        prenom = ?, " +
                    "                        email = ?, " +
                    "                        telephone = ?, " +
                    "                        rue = ?, " +
                    "                        code_postal = ?, " +
                    "                        ville = ?, " +
                    "                        mot_de_passe = ?, " +
                    "                        credit = ?, " +
                    "                        administrateur = ? " +
                    "WHERE no_utilisateur = ?;";
            PreparedStatement stmt = cnx.prepareStatement(UPDATE);
            fillPreparedStatement(utilisateur, stmt);
            stmt.setInt(12, utilisateur.getNoUtilisateur());
            stmt.executeUpdate();
            setSecurityRoles(utilisateur);
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_UPDATE);
            throw dalException;
        }
    }
    /**
     * Delete from the db from an instance of Utilisateur
     * @param utilisateur Utilisateur the instance filled with the noUtilisateur to delete
     */
    @Override
    public void delete(Utilisateur utilisateur) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?;" +
                            "DELETE FROM UTILISATEURS_ROLES WHERE pseudo = ?";
            PreparedStatement stmt = cnx.prepareStatement(DELETE);
            stmt.setInt(1, utilisateur.getNoUtilisateur());
            stmt.setString(2, utilisateur.getPseudo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_DELETE);
            throw dalException;
        }
    }

    /**
     * Set a prepared statement used by both INSERT and UPDATE requests
     * @param utilisateur Utilisateur The instance to insert or update
     * @param stmt Statement The statement to set
     * @throws SQLException If parameterIndex does not correspond to a parameter marker in the SQL statement
     */
    private void fillPreparedStatement(Utilisateur utilisateur, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, utilisateur.getPseudo());
        stmt.setString(2, utilisateur.getNom());
        stmt.setString(3, utilisateur.getPrenom());
        stmt.setString(4, utilisateur.getEmail());
        stmt.setString(5, utilisateur.getTelephone());
        stmt.setString(6, utilisateur.getRue());
        stmt.setString(7, utilisateur.getCodePostal());
        stmt.setString(8, utilisateur.getVille());
        stmt.setString(9, utilisateur.getMotDePasse());
        stmt.setInt(10, utilisateur.getCredit());
        stmt.setBoolean(11, utilisateur.isAdmimistrateur());
    }

    /**
     * Fill an instance from a ResultSet
     * @param rs the resultSet
     * @return Utilisateur The instance
     * @throws SQLException if the ResultSet doesn't match the different fields
     */
    private Utilisateur hydrateUtilisateur(ResultSet rs) throws SQLException {
        return new Utilisateur(
                rs.getInt("no_utilisateur"),
                rs.getString("pseudo"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("rue"),
                rs.getString("code_postal"),
                rs.getString("ville"),
                rs.getString("mot_de_passe"),
                rs.getInt("credit"),
                rs.getBoolean("administrateur")
        );
    }

    /**
     * Fill a SQL Table formatted for Tomcat FORM method authentication
     * @param utilisateur The user to set roles for
     * @throws SQLException If there is any format issue with the values
     */
    private void setSecurityRoles(Utilisateur utilisateur) throws SQLException {
        String ADD_ROLE = "INSERT INTO UTILISATEURS_ROLES (pseudo, nom_role) VALUES (?, ?);";
        Connection cnx = JdbcTools.connect();
        PreparedStatement stmt = cnx.prepareStatement(ADD_ROLE);
        if (utilisateur.isAdmimistrateur()) {
            //TODO : Implements here the admin role
        } else {
            stmt.setString(1, utilisateur.getPseudo());
            stmt.setString(2, "basic_user");
        }
        stmt.executeUpdate();
    }
}
