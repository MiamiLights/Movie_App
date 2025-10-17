package it.nicolacosta.movie_app.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

  private Connection connection;
  private final String DB_URL = "jdbc:mysql://localhost:3308/movie_db?allowPublicKeyRetrieval=true&useSSL=false";
  private final String DB_USER = "nicolac";
  private final String DB_PASSWORD = "password";

  public DatabaseConnectionManager() throws SQLException {
    try {
      connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() {
    return connection;
  }
}
