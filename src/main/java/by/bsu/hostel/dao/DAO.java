package by.bsu.hostel.dao;

/**
 * Created by Kate on 13.02.2016.
 */

import by.bsu.hostel.domain.Entity;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DAO<T extends Entity> {
    static Logger log = Logger.getLogger(DAO.class);
    protected ProxyConnection proxyConnection;

    public abstract List<T> findAll() throws DAOException;

    public abstract T findById(Long id) throws DAOException;

    public abstract boolean deleteById(List<String> idsList) throws DAOException;//

//    public abstract boolean delete(T entity) throws DAOException;

    public abstract boolean add(T entity) throws DAOException;

//    public abstract boolean update(T entity) throws DAOException;

    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Can't close statement" + e);
            }
        }
    }
}
