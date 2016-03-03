package by.bsu.hostel.pool;

import by.bsu.hostel.exception.PoolException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Kate on 07.02.2016.
 */
public class ConnectionPool {
    private static final int POOL_SIZE = 7;
    private static final int ATTEMPTS_LIMIT = 14;
    private static ConnectionPool connectionPool = null;
    private volatile static boolean instanceCreated = false;
    private static ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static Lock lock = new ReentrantLock();
    static Logger log = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool(final int POOL_SIZE) throws PoolException {
        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(POOL_SIZE);
        log.debug("Connecting to database...");
        int currentPoolSize = 0;
        int attemptsCount = 0;
        while (currentPoolSize < POOL_SIZE && attemptsCount < ATTEMPTS_LIMIT) {
            try {
                ProxyConnection proxyConnection = new ProxyConnection(ConnectorDB.getConnection());
                connectionQueue.offer(proxyConnection);
                currentPoolSize++;
            } catch (SQLException | ClassNotFoundException e) {
                log.debug("Can't get connection" + e);
            } finally {
                attemptsCount++;
            }
        }
        if (connectionQueue.size() != POOL_SIZE) {
            throw new PoolException("Can't create connection pool!");
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated) {
            lock.lock();
            try {
                if (!instanceCreated) {
                    connectionPool = new ConnectionPool(POOL_SIZE);
                    instanceCreated = true;
                    log.debug("Pool created!");
                }
            } catch (PoolException e) {
                log.fatal(e);
                throw new ExceptionInInitializerError("Cant get pool instance");
            } finally {
                lock.unlock();
            }
        }
        return connectionPool;
    }

    public static ProxyConnection getConnection(){
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connectionQueue.take();
        } catch (InterruptedException e) {
            log.error("Can't get connection from queue!");
        }
        return proxyConnection;
    }

    public static void returnConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }

    public static void closePool() {
        int currentPoolSize = 0;
        int attemptsCount = 0;
        while (currentPoolSize < connectionQueue.size() && attemptsCount < ATTEMPTS_LIMIT) {
            try {
                connectionQueue.poll().close();
                currentPoolSize++;
            } catch (SQLException e) {
                log.debug("Can't close connection!" + e);
            } finally {
                attemptsCount++;
            }
        }
        if (!connectionQueue.isEmpty()) {//
            log.error("Can't close pool!" + connectionQueue.size());
        }
    }
}
