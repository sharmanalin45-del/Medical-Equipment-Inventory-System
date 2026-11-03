package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection connection = null;

        try {

            String url = "jdbc:mysql://localhost:3306/medical_inventory";
            String username = "root";
            String password = "Jatin@2008";

            connection = DriverManager.getConnection(url, username, password);

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {

            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }

        return connection;
    }
}