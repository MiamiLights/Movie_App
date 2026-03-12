package it.nicolacosta.movie_app.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.nicolacosta.movie_app.events.Observer;
import it.nicolacosta.movie_app.model.Media;


public class MediaDAO{

  private static MediaDAO instance;
  private final DatabaseConnectionManager connectionManager;
  private final Connection connection;
  private final MediaMapper mapper;

  private MediaDAO() throws SQLException {
    this.connectionManager = new DatabaseConnectionManager();
    this.connection = connectionManager.getConnection();
    this.mapper = new MediaMapper();
  }

  public static MediaDAO getInstance() throws SQLException{
    if (instance == null)
      instance = new MediaDAO();
    return instance;
  }

  public void addMedia(Media media) throws SQLException {
    String query = "INSERT INTO movie_table (title, director, genre, status, episodes, seasons, year, rating, type) " +
            "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { //Try with resources
      Map<String, Object> data = mapper.mediaToMap(media);
      setStatementParameters(data, statement);
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
    try (PreparedStatement statement = connection.prepareStatement(query)) { //Try with resources
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
      try(PreparedStatement statement = connection.prepareStatement(query)) {
        Map<String, Object> data = mapper.mediaToMap(media);
        setStatementParameters(data, statement);
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
    try(PreparedStatement statement = connection.prepareStatement(query)) {
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
    try(PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery()){
      while (rs.next()) {
        mediaList.add(mapper.resultSetToMedia(rs));
      }
      return mediaList;
    }
  }
  private void setStatementParameters(Map<String, Object> data, PreparedStatement stmt) throws SQLException {
    stmt.setString(1, (String) data.get("title"));
    stmt.setString(2, (String) data.get("director"));
    stmt.setString(3, (String) data.get("genre"));
    stmt.setString(4, (String) data.get("status"));
    stmt.setObject(5, data.get("episodes"), Types.INTEGER);
    stmt.setObject(6, data.get("seasons"), Types.INTEGER);
    stmt.setInt(7, (Integer) data.get("year"));
    stmt.setInt(8, (Integer) data.get("rating"));
    stmt.setString(9, (String) data.get("type"));
  }

}
