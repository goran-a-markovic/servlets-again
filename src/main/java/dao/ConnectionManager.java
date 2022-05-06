package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ConnectionManager {
    private static Connection connection = null;

    private ConnectionManager() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            ResourceBundle bundle = ResourceBundle.getBundle("dbConfig");
            String url = bundle.getString("url");
            String username = bundle.getString("username");
            String password = bundle.getString("password");
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e){
                System.out.println("Something went wrong when creating the connection");
                e.printStackTrace();
            }
        } return connection;

    }
}
