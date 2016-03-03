package by.bsu.hostel.service;

import by.bsu.hostel.dao.ClientDAO;
import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

/**
 * Created by Kate on 15.02.2016.
 */
public class ClientService {
    static Logger log = Logger.getLogger(ClientService.class);
    private static ClientService clientService = null;

    private ClientService() {
    }

    public static ClientService getInstance() {
        if (clientService == null) {
            clientService = new ClientService();
        }
        return clientService;
    }

//    private ClientDAO clientDAO = null;

//    public List<Client> findAll() throws ServiceException, SQLException {
//        conn = ConnectionPool.getConnection();
//        if (conn != null) {
//            clientDAO = new ClientDAO(conn);
//            List<Client> list = clientDAO.findAll();
//            ConnectionPool.returnConnection(conn);
//            return list;
//        }
//        throw new ServiceException("Can't get connection");//try later or smth
//    }

    //    public List<Client> authorize(Authorization authorization) throws ServiceException {
//        conn = ConnectionPool.getConnection();
//        if (conn != null) {
//            clientDAO = new ClientDAO(conn);
//    }
    public Client logIn(Authentication authentication) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        Client client = null;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                client = clientDAO.findByLogPass(authentication);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");//
        }
        return client;
    }

    public boolean register(Client client) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                return clientDAO.add(client);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
            return false;
        }
    }

//    public boolean logOut(){
//        return true;
//    }
}

