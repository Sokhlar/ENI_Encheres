package fr.eni.projet_encheres.dal.jdbc;

import fr.eni.projet_encheres.bo.ArticleVendu;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.DAOArticleVendu;
import fr.eni.projet_encheres.dal.ErrorCodesDAL;

import java.sql.*;
import java.util.List;

public class ArticleVenduDAOJdbcImpl implements DAOArticleVendu {
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

    @Override
    public ArticleVendu selectById(int id) throws DALException {
        return null;
    }

    @Override
    public List<ArticleVendu> selectAll() throws DALException {
        return null;
    }

    @Override
    public void update(ArticleVendu articleVendu) throws DALException {

    }

    @Override
    public void delete(ArticleVendu articleVendu) throws DALException {

    }

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
}
