import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/ufc";
    private static final String USER = "root";
    private static final String PASSWORD = "batman123";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Fighter buscarPorNome(String nome) {
        String sql = "SELECT * FROM fighters WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Fighter(
                        rs.getString("name"),
                        rs.getInt("victories"),
                        rs.getInt("defeats"),
                        rs.getInt("draws"),
                        rs.getInt("knockout")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
