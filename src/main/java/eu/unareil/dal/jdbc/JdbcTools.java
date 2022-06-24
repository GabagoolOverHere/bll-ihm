package eu.unareil.dal.jdbc;

import eu.unareil.dal.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTools {

    public static Connection conn = null;

    public static Connection getConnection() throws DALException {
        final String SERVER = Settings.getProperty("server");
        final String PORT = Settings.getProperty("port");
        final String DB = Settings.getProperty("db");
        final String USER = Settings.getProperty("user");
        final String PASSWORD = Settings.getProperty("password");
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(String.format("jdbc:mariadb://%s:%s/%s?user=%s&password=%s", SERVER, PORT, DB, USER, PASSWORD));
            } catch (SQLException e) {
                throw new DALException("Erreur de connexion à la base de données", e.getCause());
            }
        }
        return conn;
    }
}
