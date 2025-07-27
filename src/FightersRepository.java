import org.json.JSONObject;

import java.awt.font.FontRenderContext;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FightersRepository {

    public static int countFighters(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM fighters");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int total = rs.getInt(1);
        rs.close();
        stmt.close();
        return total;
    }

    public static boolean idsFromAPIExistInDB(Connection conn, JSONObject apiData) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT ID FROM fighters");
        ResultSet rs = stmt.executeQuery();

        Set<String> ids = new HashSet<>();
        while (rs.next()) {
            ids.add(rs.getString("id"));
        }
        rs.close();
        stmt.close();

        for (String id : apiData.keySet()) {
            if (!ids.contains(id)) {
                return false;
            }

        }
        return true;
    }

    public static void updateFighters(Connection conn, JSONObject apiData) throws SQLException {
        String sql = "INSERT INTO fighters (id, name, nickname, category, status, wins, losses, draws, age, height, weight, reach, legReach, placeOfBirth, trainsAt, fightingStyle, octagonDebut, imgUrl) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "name = VALUES(name), nickname = VALUES(nickname), category = VALUES(category), status = VALUES(status), " +
                "wins = VALUES(wins), losses = VALUES(losses), draws = VALUES(draws), age = VALUES(age), " +
                "height = VALUES(height), weight = VALUES(weight), reach = VALUES(reach), legReach = VALUES(legReach), " +
                "placeOfBirth = VALUES(placeOfBirth), trainsAt = VALUES(trainsAt), fightingStyle = VALUES(fightingStyle), " +
                "octagonDebut = VALUES(octagonDebut), imgUrl = VALUES(imgUrl)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        for (String id : apiData.keySet()) {
            JSONObject f = apiData.getJSONObject(id);

            stmt.setString(1, id);
            stmt.setString(2, f.optString("name"));
            stmt.setString(3, f.optString("nickname"));
            stmt.setString(4, f.optString("category"));
            stmt.setString(5, f.optString("status"));
            stmt.setString(6, f.optString("wins"));
            stmt.setString(7, f.optString("losses"));
            stmt.setString(8, f.optString("draws"));
            stmt.setString(9, f.optString("age"));
            stmt.setString(10, f.optString("height"));
            stmt.setString(11, f.optString("weight"));
            stmt.setString(12, f.optString("reach"));
            stmt.setString(13, f.optString("legReach"));
            stmt.setString(14, f.optString("placeOfBirth"));
            stmt.setString(15, f.optString("trainsAt"));
            stmt.setString(16, f.optString("fightingStyle"));
            stmt.setString(17, f.optString("octagonDebut"));
            stmt.setString(18, f.optString("imgUrl"));

            stmt.executeUpdate();
        }
        stmt.close();

    }
}
