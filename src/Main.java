import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;

import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        Fighter fighter1 = new Fighter("Jon Jones", 80, 10, 10, 5);
        Fighter fighter2 = new Fighter("Stipe Miocic", 80, 10, 10, 15);

        Fight fight = new Fight(fighter1, fighter2);
        Fighter winner = fight.simulate();


        fighter1.sheet();
        fighter2.sheet();

        System.out.println("o vencedor é: " + winner.getName());

        Fighter f = Database.searchByName("Jon Jones");
        if (f != null) {
            f.sheet();
        } else {
            System.out.println("Lutador não encontrado.");
        }


        String apiUrl = "https://api.octagon-api.com/fighters";

        String dbUrl = "jdbc:mysql://localhost:3306/ufc";
        String dbUser = "root";
        String dbPassword = "batman123";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                response.append(linha);
            }
            reader.close();

            JSONObject fightersJson = new JSONObject(response.toString());
            Connection dbConn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            String sql = "INSERT INTO fighters (id, name, nickname, category, status, wins, losses, draws, age, height, weight, reach, legReach, placeOfBirth, trainsAt, fightingStyle, octagonDebut, imgUrl) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " + "ON DUPLICATE KEY UPDATE " + "name = VALUES(name), nickname = VALUES(nickname), category = VALUES(category), status = VALUES(status), " + "wins = VALUES(wins), losses = VALUES(losses), draws = VALUES(draws), age = VALUES(age), " + "height = VALUES(height), weight = VALUES(weight), reach = VALUES(reach), legReach = VALUES(legReach), " + "placeOfBirth = VALUES(placeOfBirth), trainsAt = VALUES(trainsAt), fightingStyle = VALUES(fightingStyle), " + "octagonDebut = VALUES(octagonDebut), imgUrl = VALUES(imgUrl)";

            PreparedStatement stmt = dbConn.prepareStatement(sql);

            Iterator<String> keys = fightersJson.keys();
            while (keys.hasNext()) {
                String id = keys.next();
                JSONObject fighterData = fightersJson.getJSONObject(id);

                stmt.setString(1, id);
                stmt.setString(2, fighterData.optString("name"));
                stmt.setString(3, fighterData.optString("nickname"));
                stmt.setString(4, fighterData.optString("category"));
                stmt.setString(5, fighterData.optString("status"));
                stmt.setString(6, fighterData.optString("wins"));
                stmt.setString(7, fighterData.optString("losses"));
                stmt.setString(8, fighterData.optString("draws"));
                stmt.setString(9, fighterData.optString("age"));
                stmt.setString(10, fighterData.optString("height"));
                stmt.setString(11, fighterData.optString("weight"));
                stmt.setString(12, fighterData.optString("reach"));
                stmt.setString(13, fighterData.optString("legReach"));
                stmt.setString(14, fighterData.optString("placeOfBirth"));
                stmt.setString(15, fighterData.optString("trainsAt"));
                stmt.setString(16, fighterData.optString("fightingStyle"));
                stmt.setString(17, fighterData.optString("octagonDebut"));
                stmt.setString(18, fighterData.optString("imgUrl"));

                stmt.executeUpdate();
                System.out.println("Atualizado/Injetado: " + fighterData.optString("name"));
            }

            stmt.close();
            dbConn.close();
            System.out.println("✅ Importação finalizada!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}