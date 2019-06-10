package fr.eni.projet_encheres.dal.jdbc;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.dao.DAOEnchere;
import fr.eni.projet_encheres.dal.ErrorCodesDAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements DAOEnchere {
    @Override
    public void insert(Enchere enchere) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = cnx.prepareStatement(INSERT);
            stmt.setInt(1, enchere.getNoUtilisateur());
            stmt.setInt(2, enchere.getNoArticle());
            stmt.setObject(3, new Timestamp(enchere.getDateEnchere().getTime()));
            stmt.setInt(4, enchere.getMontantEnchere());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_INSERT);
            throw dalException;
        }
    }

    /**
     * Return an ArrayList filled by no_articles that matched
     * both a state and a user. i.e. All articles with current auctions for a particular user
     * @param utilisateur Utilisateur The user
     * @param state String The State ("EC", "AN" or "VE")
     * @return ArrayList<Integer> all the id of the articles that matched
     * @throws DALException If there is any issue with the SQL query
     */
    @Override
    public List<Integer> getNoArticlesByUtilisateurAndEtat(Utilisateur utilisateur, String state) throws DALException {
        Connection cnx = JdbcTools.connect();
        List <Integer> noArticlesMatched = new ArrayList<>();

        String SELECT_BY_UTILISATEUR_AND_ETAT = "SELECT E.no_article " +
                "FROM ENCHERES E " +
                "INNER JOIN ARTICLES_VENDUS AV on E.no_article = AV.no_article " +
                "WHERE AV.etat_vente = ? AND E.no_utilisateur = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_UTILISATEUR_AND_ETAT);
            stmt.setString(1, state);
            stmt.setInt(2, utilisateur.getNoUtilisateur());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                noArticlesMatched.add(rs.getInt("no_article"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_INSERT);
            throw dalException;
        }

        return noArticlesMatched;
    }

    @Override
    public Enchere selectById(int id) throws DALException {
        return null;
    }

    @Override
    public List<Enchere> selectAll() throws DALException {
        return null;
    }

    @Override
    public void update(Enchere enchere) throws DALException {

    }

    @Override
    public void delete(Enchere enchere) throws DALException {

    }

}
