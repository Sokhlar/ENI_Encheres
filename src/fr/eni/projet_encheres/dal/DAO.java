package fr.eni.projet_encheres.dal;

import java.util.List;
/**
 * Implementation of DAO Design Pattern with basic CRUD
 * @param <T>
 */
public interface DAO<T> {
    void insert(T var) throws DALException;
    T selectById(int id) throws DALException;
    List<T> selectAll() throws DALException;
    void update(T var) throws DALException;
    void delete(T var) throws DALException;
}
