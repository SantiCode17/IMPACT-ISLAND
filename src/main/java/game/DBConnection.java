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
