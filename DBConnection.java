import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/library_db";
        String username = "root";
        String password = "Suri@9182";

        try {

            Connection connection =
                    DriverManager.getConnection(url, username, password);

            System.out.println("Database connected successfully!");

            return connection;

        } catch (Exception e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();

            return null;
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}