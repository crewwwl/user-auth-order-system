import java.sql.*;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Adding details to log in
            String url = "jdbc:sqlserver://localhost:1433;databaseName=CarMarketPlaceDB;encrypt=false";
            String user = "crewwwl"; 
            String password = "Ansar0102"; 

            // Connecting
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅Successful connection to the database!");

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Connection error:");
            e.printStackTrace();
        }
    }
}
