import java.sql.*;
import org.json.JSONObject;

public class attDatabase {

    public static void execute() {
        try (Connection conn = DatabaseConnection.connect()) {
            JSONObject fightersJson = ApiClient.getFighters();

            boolean updated = FightersRepository.idsFromAPIExistInDB(conn, fightersJson);
            if (updated) {
                System.out.println("Banco já está atualizado.");
                return;
            }

            System.out.println("Atualizando banco com dados da API...");
            FightersRepository.updateFighters(conn, fightersJson);
            System.out.println("Atualização do banco de dados concluída!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


