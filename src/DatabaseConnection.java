import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ufc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "batman123";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
