package by.bsu.hostel.pool;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kate on 20.02.2016.
 */
public class ConnectorDB {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        DriverManager.registerDriver(new Driver());
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
