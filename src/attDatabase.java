import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Iterator;

import org.json.JSONObject;

public class attDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ufc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "batman123";
    private static final String API_URL = "https://api.octagon-api.com/fighters";

    public static void execute() {
        try (
                Connection dbConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        ) {
            int quantityDatabase = countFightersOnDatabase(dbConn);

            JSONObject fightersJson = getDataApi();
            int quantityAPI = fightersJson.length();

            if (!APIIdAreInTheBank(dbConn, fightersJson)) {
                System.out.println(" Atualizando banco com dados da API...");
            } else {
                System.out.println(" Banco já está atualizado.");
                return;
            }


            String sql = "INSERT INTO fighters (id, name, nickname, category, status, wins, losses, draws, age, height, weight, reach, legReach, placeOfBirth, trainsAt, fightingStyle, octagonDebut, imgUrl) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "name = VALUES(name), nickname = VALUES(nickname), category = VALUES(category), status = VALUES(status), " +
                    "wins = VALUES(wins), losses = VALUES(losses), draws = VALUES(draws), age = VALUES(age), " +
                    "height = VALUES(height), weight = VALUES(weight), reach = VALUES(reach), legReach = VALUES(legReach), " +
                    "placeOfBirth = VALUES(placeOfBirth), trainsAt = VALUES(trainsAt), fightingStyle = VALUES(fightingStyle), " +
                    "octagonDebut = VALUES(octagonDebut), imgUrl = VALUES(imgUrl)";

            PreparedStatement stmt = dbConn.prepareStatement(sql);

            Iterator<String> keys = fightersJson.keys();
            while (keys.hasNext()) {
                String id = keys.next();
                JSONObject f = fightersJson.getJSONObject(id);

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
            System.out.println("Atualização concluida!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int countFightersOnDatabase(Connection conn) throws SQLException {
        System.out.println("contando lutadores");
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM fighters");
        ResultSet rs = stmt.executeQuery();
        int total = 0;
        if (rs.next()) {
            total = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return total;
    }

    private static JSONObject getDataApi() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String row;
        while ((row = reader.readLine()) != null) {
            response.append(row);
        }
        reader.close();
        return new JSONObject(response.toString());
    }

    private static boolean APIIdAreInTheBank(Connection conn, JSONObject apiData) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM fighters");
        ResultSet rs = stmt.executeQuery();

        java.util.Set<String> idsNoBanco = new java.util.HashSet<>();
        while (rs.next()) {
            idsNoBanco.add(rs.getString("id"));
        }
        rs.close();
        stmt.close();

        for (String id : apiData.keySet()) {
            if (!idsNoBanco.contains(id)) {
                System.out.println(" achou um id que está na API e não está no banco");
                return false;
            }
        }
        System.out.println("todos os ids da API existem no banco");
        return true;
    }
}


