package by.bsu.hostel.pool;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Kate on 07.02.2016.
 */
public class ConnectionPool {
    private final int POOL_SIZE = 5;
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/hostel_system";
    private final String USER = "root";
    private final String PASS = "";
    private ProxyConnection proxyConnection;
    private Statement stmt;

        private ArrayBlockingQueue<ProxyConnection> connectionQueue;
        public ConnectionPool(final int POOL_SIZE) throws SQLException {
            connectionQueue = new ArrayBlockingQueue<ProxyConnection>(POOL_SIZE);
            System.out.println("Connecting to database...");
            int i = 0;
            while (i < POOL_SIZE) {//?
                try {
                    Class.forName(JDBC_DRIVER);
                    proxyConnection = new ProxyConnection(DriverManager.getConnection(DB_URL, USER, PASS));
                    connectionQueue.offer(proxyConnection);
                    i++;//?
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    public ProxyConnection getConnection() throws InterruptedException {
            ProxyConnection connection = null;
            connection = connectionQueue.poll();//!
            return connection;
        }
        public void closeConnection(ProxyConnection connection) {
            connectionQueue.offer(connection); // возможны утечки соединений
        }
// more methods

}
