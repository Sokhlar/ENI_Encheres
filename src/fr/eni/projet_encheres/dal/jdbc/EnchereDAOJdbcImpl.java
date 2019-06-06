package fr.eni.projet_encheres.dal.jdbc;

import fr.eni.projet_encheres.bo.Enchere;
import fr.eni.projet_encheres.dal.DALException;
import fr.eni.projet_encheres.dal.dao.DAOEnchere;
import fr.eni.projet_encheres.dal.ErrorCodesDAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
