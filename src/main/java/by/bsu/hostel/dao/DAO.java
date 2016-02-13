package by.bsu.hostel.dao;

/**
 * Created by Kate on 13.02.2016.
 */

import by.bsu.hostel.domain.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
public abstract class DAO <K, T extends Entity> {
    public abstract List<T> findAll();
    public abstract T findEntityById(K id);
    //public abstract boolean delete(K id);
    public abstract boolean delete(T entity);
    public abstract boolean create(T entity);
    public abstract T update(T entity);
    //??
    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
// лог о невозможности закрытия Statement
        }
    }
    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
// генерация исключения, т.к. нарушается работа пула
        }
    }
}
