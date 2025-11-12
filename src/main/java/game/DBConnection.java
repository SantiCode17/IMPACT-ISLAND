package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final Connection con;

    private DBConnection() throws SQLException {
        final String url = String.format(
                "jdbc:mysql://%s:%s/%s",
                System.getenv("DB_HOST"),
                System.getenv("DB_PORT"),
                System.getenv("DB_NAME")
        );

        final String user = System.getenv("DB_USER");
        final String password = System.getenv("DB_PASSWORD");

        this.con = DriverManager.getConnection(url, user, password);
    }

    public void createGame(String nombre) {
        final var sql = "INSERT INTO games(name) VALUES (?)";
        try (final var stmt = this.con.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        } catch (SQLException _) {
            System.err.println("No se pudo crear el registro de la partida.");
        }
    }

    public void createAdvancement(String advancementName, int gameId) {
        final var sql = "INSERT INTO games_advancements(advancement_name, game_id) VALUES (?, ?)";
        try (final var stmt = this.con.prepareStatement(sql)) {
            stmt.setString(1, advancementName);
            stmt.setInt(2, gameId);
        } catch (SQLException _) {
            System.err.println("No se pudo crear el registro del logro.");
        }
    }

    // Singleton related
    private static DBConnection instance = null;

    public static void connect() throws SQLException {
        assert instance == null : "Ya te has conectado a la BBDD.";
        instance = new DBConnection();
    }

    public static DBConnection getInstance() {
        assert instance != null : "No te has conectado a la BBDD.";
        return instance;
    }
}
