package it.nicolacosta.movie_app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nicolacosta.movie_app.events.Observer;
import it.nicolacosta.movie_app.events.Subject;
import it.nicolacosta.movie_app.factory.MovieFactory;
import it.nicolacosta.movie_app.factory.TvSeriesFactory;
import it.nicolacosta.movie_app.model.Media;

public class MediaDAO implements Subject {

  private final List<Observer> observers = new ArrayList<>();
  DatabaseConnectionManager connectionManager;
  Connection connection;

  public MediaDAO() throws SQLException {
    this.connectionManager = new DatabaseConnectionManager();
    this.connection = connectionManager.getConnection();
  }

  public void addMedia(Media media) throws SQLException {
    String query = "INSERT INTO movie_table (" + media.getInsertColumnNames()+ ") VALUES ("+ media.getInsertValuePlaceholders() + ")";
    PreparedStatement statement = connection.prepareStatement(query);
    media.setInsertStatementParams(statement);
  }

  public void deleteMedia(Media media) throws SQLException {
    String query = "DELETE FROM movie_table WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, media.getId());
    int rowsAffected = statement.executeUpdate();

    if (rowsAffected > 0) {
      System.out.println("Cancellazione completata");
    }
  }

  public void editMedia(Media media) throws SQLException{
      String setters = media.getUpdateQuerySetters();
      String query = "UPDATE movie_table SET " + setters + " WHERE id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      int idIndex = media.setUpdateQueryParams(statement, 1);
      statement.setInt(idIndex, media.getId());

      int rowsAffected = statement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Modifica per ID " + media.getId() + " completata con successo.");
      } else {
        System.out.println("Nessuna riga modificata per ID " + media.getId() + ". L'oggetto potrebbe non esistere.");
      }
  }

  public Media getMedia(Media media) throws SQLException {
    String query = "SELECT * FROM movie_table WHERE id = ?";
    Media mediaReturn = null;
    Map<String, Object> mediaMap;
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, media.getId());
    ResultSet rs = statement.executeQuery();
    if (rs.next()) {
      mediaMap = convertRsToMediaMap(rs);
      if (mediaMap.get("type").equals("tvSeries"))
        mediaReturn = new TvSeriesFactory().createFromData(mediaMap);
      else if (mediaMap.get("type").equals("movie"))
        media = new MovieFactory().createFromData(mediaMap);
      else
        throw new IllegalArgumentException();
    }
    return mediaReturn;
  }

  public List<Media> getAllMedia() throws SQLException{
    List<Media> mediaList = new ArrayList<>();
    String query = "SELECT * FROM movie_table";
    Map<String, Object> mediaMap;

      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        mediaMap = convertRsToMediaMap(rs);
        System.out.println(mediaMap);
        if (mediaMap.get("type").equals("tvSeries"))
          mediaList.add(new TvSeriesFactory().createFromData(mediaMap));
        else if (mediaMap.get("type").equals("movie"))
          mediaList.add(new MovieFactory().createFromData(mediaMap));
        else
          throw new IllegalArgumentException();
      }
    return mediaList;
  }

  /*
   * Usiamo la conversione ad HashMap e non direttamente result set per non
   * accoppiare troppo le classi Concrete Factory al DAO,
   * in questo modo le rendiamo riutilizzabili per gestire dati che non sono solo
   * provenienti da database (Result set) ma anche
   * dati provenienti da file json o altri formati che possono essere
   * convenientemente convertiti
   * in HashMap.
   */

  private Map<String, Object> convertRsToMediaMap(ResultSet rs) throws SQLException{
    Map<String, Object> result = new HashMap<>();
      result.put("id", rs.getInt("id"));
      result.put("title", rs.getString("title"));
      result.put("director", rs.getString("director"));
      result.put("genre", rs.getString("genre"));
      result.put("status", rs.getString("status"));
      result.put("year", rs.getInt("year"));
      result.put("rating", rs.getInt("rating"));
      result.put("type", rs.getString("type"));

      if (result.get("type").equals("tvSeries")) {
        result.put("episodes", rs.getInt("episodes"));
        result.put("seasons", rs.getInt("seasons"));
      }
    return result;
  }

  @Override
  public void addObserver(Observer observer) {
    if (!observers.contains(observer))
      observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers)
      observer.onDatabaseChanged();
  }
}
