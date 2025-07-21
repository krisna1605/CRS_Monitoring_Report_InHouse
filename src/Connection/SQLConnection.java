package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    // URL JDBC SQL
    private static final String URL = "jdbc:sqlserver://192.168.10.95:1433;databaseName=LOSSME;encrypt=true;trustServerCertificate=true";
    
    

    // Set Username & Password
    private static final String USER = "PSAS";
    private static final String PASSWORD = "asolcorp";
    //asolcorp Pass Prod
    //PSAS
    

    // Method Get Connection
    public Connection getConnection() {
        Connection connection = null;

        try {
            // Load driver AS400
            Class.forName("com.mysql.jdbc.Driver");

            // Open Connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to : " + URL);

        } catch (SQLException se) {
            
            System.out.println("Failure Connection: " + se.getMessage());
        } catch (ClassNotFoundException e) {
            
            System.out.println("Driver Not Found: " + e.getMessage());

        } /*finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
            */

            // Return Connection
            return connection;
        }
           
    }

