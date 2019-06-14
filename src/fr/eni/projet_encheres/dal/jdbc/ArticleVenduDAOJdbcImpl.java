package fr.eni.projet_encheres.dal.jdbc;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.bo.Categorie;
import fr.eni.projet_encheres.bo.Utilisateur;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.dao.DAOArticleVendu;
import fr.eni.projet_encheres.dal.ErrorCodesDAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleVenduDAOJdbcImpl implements DAOArticleVendu {
    /**
     * Insert an instance into the DB and fill this instance with the id generated by mssql server
     * @param  articleVendu ArticleVendu The instance to insert into the DB
     * @throws DALException if the SQL INSERT request is wrong
     */
    @Override
    public void insert(ArticleVendu articleVendu) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String INSERT = "INSERT INTO ARTICLES_VENDUS " +
                    "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_vente, no_utilisateur, no_categorie) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            fillPreparedStatement(articleVendu, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                articleVendu.setNoArticle(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_INSERT);
            throw dalException;
        }
    }

    /**
     * Extract an instance of ArticleVendu from the DB with the noArticle of it
     * @param id The noArticle
     * @return An instance of ArticleVendu
     * @throws DALException If there is any issu with the SQL query
     */
    @Override
    public ArticleVendu selectById(int id) throws DALException {
        Connection cnx = JdbcTools.connect();
        ArticleVendu articleVendu = null;
        try {
            String SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                articleVendu = hydrateArticleVendu(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return articleVendu;
    }

    /**
     * Get all ArticleVendu instances that match a category from the DB
     * @param categorie the filter
     * @return an ArrayList filled with instances
     * @throws DALException if the SQL SELECT request is wrong
     */
    @Override
    public List<ArticleVendu> filterByCategory(Categorie categorie) throws DALException {
        Connection cnx = JdbcTools.connect();
        List<ArticleVendu> articlesVendus = new ArrayList<>();
        try {
            String SELECT_BY_CATEGORY = "SELECT * " +
                    "FROM ARTICLES_VENDUS " +
                    "INNER JOIN CATEGORIES C on ARTICLES_VENDUS.no_categorie = C.no_categorie " +
                    "WHERE C.no_categorie = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_CATEGORY);
            stmt.setInt(1, categorie.getNoCategorie());
            stmt.execute();
            ResultSet rs =  stmt.getResultSet();
            while (rs.next()) {
                articlesVendus.add(hydrateArticleVendu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return articlesVendus;
    }

    /**
     * Get every ArticleVendu from the DB from a particular state
     * @param etat String Values : EC, VA or AN
     * @return An ArrayList
     * @throws DALException If there is any issue with the SQL query
     */
    public List<Integer> filterByEtat(String etat) throws DALException {
        Connection cnx = JdbcTools.connect();
        List<Integer> articlesVendus = new ArrayList<>();
        try {
            String SELECT_BY_ETAT = "SELECT AV.no_article FROM ARTICLES_VENDUS AV WHERE AV.etat_vente = ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ETAT);
            stmt.setString(1, etat);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                articlesVendus.add(rs.getInt("no_article"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return articlesVendus;
    }

    @Override
    public List<Integer> getArticlesFromASellerAndState(Utilisateur utilisateur, String state) throws DALException {
        Connection cnx = JdbcTools.connect();
        List <Integer> articleVendus = new ArrayList<>();

        String SELECT_BY_SELLER_AND_STATE = "SELECT AV.no_article FROM UTILISATEURS U " +
                "INNER JOIN ARTICLES_VENDUS AV on U.no_utilisateur = AV.no_utilisateur " +
                "WHERE U.no_utilisateur = ? AND AV.etat_vente = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_SELLER_AND_STATE);
            stmt.setInt(1, utilisateur.getNoUtilisateur());
            stmt.setString(2, state);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                articleVendus.add(rs.getInt("no_article"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return articleVendus;
    }


    /**
     *
     * Get all ArticleVendu instances that the field no_article match a String from the DB
     * @param filter the filter
     * @return An ArrayList filled with instances
     * @throws DALException if the SQL SELECT request is wrong
     */
    @Override
    public List<ArticleVendu> filterByString(String filter) throws DALException {
        Connection cnx = JdbcTools.connect();
        List<ArticleVendu> articlesVendus = new ArrayList<>();
        try {
            String SELECT_BY_NAME_SEARCH = "SELECT * " +
                    "FROM ARTICLES_VENDUS " +
                    "WHERE nom_article LIKE ?";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_NAME_SEARCH);
            stmt.setString(1, "%" + filter + "%");
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
                articlesVendus.add(hydrateArticleVendu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;
        }
        return articlesVendus;
    }
    /**
     * Select all the articleVendu from the DB
     * @return An ArrayList filled with instances of Utilisateur
     */
    @Override
    public List<ArticleVendu> selectAll() throws DALException {
        Connection cnx = JdbcTools.connect();
        List<ArticleVendu> articlesVendus = new ArrayList<>();
        try {
            String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
            PreparedStatement stmt = cnx.prepareStatement(SELECT_ALL);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                articlesVendus.add(hydrateArticleVendu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_SELECT);
            throw dalException;        }
        return articlesVendus;
    }

    @Override
    public void update(ArticleVendu articleVendu) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String UPDATE = "UPDATE ARTICLES_VENDUS SET " +
                    "nom_article = ?, " +
                    "description = ?, " +
                    "date_debut_encheres = ?, " +
                    "date_fin_encheres = ?, " +
                    "prix_initial = ?, " +
                    "prix_vente = ?, " +
                    "etat_vente = ?, " +
                    "no_utilisateur = ?, " +
                    "no_categorie = ? " +
                    "WHERE no_article= ?;";
            PreparedStatement stmt = cnx.prepareStatement(UPDATE);
            fillPreparedStatement(articleVendu, stmt);
            stmt.setInt(10, articleVendu.getNoArticle());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_UPDATE);
            throw dalException;
        }
    }

    /**
     * Update the current price of an article
     * @param noArticle int the id of the article to update
     * @param newPrice int the new price
     * @throws DALException if there is any issue with the SQL query
     */
    @Override
    public void updateCurrentPrice(int noArticle, int newPrice) throws DALException {
        Connection cnx = JdbcTools.connect();
        try {
            String UPDATE_CURRENT_PRICE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
            PreparedStatement stmt = cnx.prepareStatement(UPDATE_CURRENT_PRICE);
            stmt.setInt(1, newPrice);
            stmt.setInt(2, noArticle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodesDAL.ERROR_SQL_UPDATE);
            throw dalException;
        }
    }

    @Override
    public void delete(ArticleVendu articleVendu) throws DALException {
        Connection cnx = JdbcTools.connect();
        String DELETE = "DELETE FROM RETRAITS WHERE no_article = ? " +
                "DELETE FROM ARTICLES_VENDUS WHERE no_article = ? ";
        try {
            PreparedStatement stmt = cnx.prepareStatement(DELETE);
            stmt.setInt(1, articleVendu.getNoArticle());
            stmt.setInt(2, articleVendu.getNoArticle());
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
     * @param articleVendu The instance to insert or update
     * @param stmt Statement The statement to set
     * @throws SQLException If parameterIndex does not correspond to a parameter marker in the SQL statement
     */
    private void fillPreparedStatement(ArticleVendu articleVendu, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, articleVendu.getNomArticle());
        stmt.setString(2, articleVendu.getDescription());
        // https://stackoverflow.com/questions/6777810/a-datetime-equivalent-in-java-sql-is-there-a-java-sql-datetime
        stmt.setObject(3, new Timestamp(articleVendu.getDateDebutEncheres().getTime()));
        stmt.setObject(4, new Timestamp(articleVendu.getDateFinEncheres().getTime()));
        stmt.setInt(5, articleVendu.getPrixInitial());
        stmt.setInt(6, articleVendu.getPrixVente());
        stmt.setString(7, articleVendu.getEtatVente());
        stmt.setInt(8, articleVendu.getNoUtilisateur());
        stmt.setInt(9, articleVendu.getNoCategorie());
    }
    /**
     * Fill an instance from a ResultSet
     * @param rs the resultSet
     * @return articleVendu The instance
     * @throws SQLException if the ResultSet doesn't match the different fields
     */
    private ArticleVendu hydrateArticleVendu(ResultSet rs) throws SQLException {
        return new ArticleVendu(
                rs.getInt("no_article"),
                rs.getString("nom_article"),
                rs.getString("description"),
                rs.getDate("date_debut_encheres"),
                rs.getDate("date_fin_encheres"),
                rs.getInt("prix_initial"),
                rs.getInt("prix_vente"),
                rs.getString("etat_vente"),
                rs.getInt("no_utilisateur"),
                rs.getInt("no_categorie")
        );
    }
}
