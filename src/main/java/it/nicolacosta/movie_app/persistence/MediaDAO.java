package it.nicolacosta.movie_app.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.nicolacosta.movie_app.model.Media;


public class MediaDAO{

  private final DatabaseConnectionManager connectionManager;
  private final MediaMapper mapper;

  public MediaDAO(DatabaseConnectionManager databaseConnectionManager) throws SQLException {
    this.connectionManager = databaseConnectionManager;
    this.mapper = new MediaMapper();
  }

  public void addMedia(Media media) throws SQLException {
    String query = "INSERT INTO movie_table (title, director, genre, status, episodes, seasons, year, rating, type) " +
            "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
    //Creiamo la connessione nel try così che venga chiusa automaticamente alla fine, viene aperta solo quando serve
    try (Connection connection = connectionManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { //Try with resources
      mapper.mapToStatement(media, statement);
      statement.executeUpdate();

      try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()){
          media.setId(generatedKeys.getInt(1));
        }
      }
    }
  }

  public void deleteMedia(int id) throws SQLException {
    String query = "DELETE FROM movie_table WHERE id = ?";
    try (Connection connection = connectionManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) { //Try with resources
      statement.setInt(1, id);
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Cancellazione completata");
      }
    }
  }

  public void editMedia(Media media) throws SQLException{
    String query = "UPDATE movie_table SET title=?, director=?, genre=?, status=?, episodes=?, seasons=?, year=?, rating=?, type=? " +
            "WHERE id=?";
      try(Connection connection = connectionManager.getConnection();
          PreparedStatement statement = connection.prepareStatement(query)) {
        mapper.mapToStatement(media, statement);
        statement.setInt(10, media.getId());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println("Modifica per ID " + media.getId() + " completata con successo.");
        } else {
          System.out.println("Nessuna riga modificata per ID " + media.getId() + ". L'oggetto potrebbe non esistere.");
        }
      }
  }

  public Media getMedia(int id) throws SQLException {
    String query = "SELECT * FROM movie_table WHERE id = ?";
    try(Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, id);
      try(ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return mapper.resultSetToMedia(rs);
        }
        return null;
      }
    }
  }

  public List<Media> getAllMedia() throws SQLException{
    List<Media> mediaList = new ArrayList<>();
    String query = "SELECT * FROM movie_table";
    try(Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery()){
      while (rs.next()) {
        mediaList.add(mapper.resultSetToMedia(rs));
      }
      return mediaList;
    }
  }

}
