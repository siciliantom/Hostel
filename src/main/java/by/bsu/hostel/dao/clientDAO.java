//package by.bsu.hostel.dao;
//
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//
///**
// * Created by Kate on 13.02.2016.
// */
//public class clientDAO extends DAO {
//    public static final String SQL_SELECT_ALL_ABONENTS = "SELECT * FROM phonebook";
//
//    public AbonentDAO() {
//        this.connector = new WrapperConnector();
//    }
//
//    public List<Abonent> findAll() {
//        List<Abonent> abonents = new ArrayList<>();
//        Statement st = null;
//        try {
//            st = connector.getStatement();
//            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_ABONENTS);
//            while (resultSet.next()) {
//                Abonent abonent = new Abonent();
//                abonent.setId(resultSet.getInt("idphonebook"));
//                abonent.setPhone(resultSet.getInt("phone"));
//                abonent.setLastName(resultSet.getString("lastname"));
//                abonents.add(abonent);
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL exception (request or table failed): " + e);
//        } finally {
//            this.closeStatement(st);
//        }
//        return abonents;
//    }
//}
