package it.nicolacosta.movie_app.persistence;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

  private final Properties properties = new Properties();

  public DatabaseConnectionManager() {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")){
      if (input == null)
        throw new RuntimeException("Impossibile trovare db.properties");
      properties.load(input);
    }catch (IOException e){
      throw new RuntimeException("Errore durante il caricamento della configurazione database", e);
    }
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.user"),
            properties.getProperty("db.password")
    );
  }
}
