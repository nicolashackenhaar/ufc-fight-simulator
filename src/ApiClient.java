import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String API_URL = "https://api.octagon-api.com/fighters";

    private static JSONObject getFighters() throws Exception {
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
}
