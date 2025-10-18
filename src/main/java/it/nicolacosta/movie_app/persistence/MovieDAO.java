package it.nicolacosta.movie_app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.nicolacosta.movie_app.factory.MovieFactory;
import it.nicolacosta.movie_app.factory.TvSeriesFactory;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;
import it.nicolacosta.movie_app.model.TvSeries;

public class MovieDAO {

  DatabaseConnectionManager connectionManager;
  Connection connection;

  public MovieDAO() throws SQLException {
    this.connectionManager = new DatabaseConnectionManager();
    this.connection = connectionManager.getConnection();
  }

  public void addMedia(Media media) throws SQLException {
    if (media instanceof Movie) {
      Movie movie = (Movie) media;
      String query = "INSERT INTO movie_table (title, director, year, genre, status, rating, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, movie.getTitle());
      statement.setString(2, movie.getDirector());
      statement.setInt(3, movie.getYear());
      statement.setString(4, movie.getGenre());
      statement.setString(5, movie.getStatus());
      statement.setInt(6, movie.getRating());
      statement.setString(7, movie.getType());

      int rowsAffected = statement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("Inserimento completato");
      }
    }
    if (media instanceof TvSeries) {
      TvSeries tvSeries = (TvSeries) media;
      String query = "INSERT INTO movie_table (title, director, year, genre, status, episodes, seasons, rating, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, tvSeries.getTitle());
      statement.setString(2, tvSeries.getDirector());
      statement.setInt(3, tvSeries.getYear());
      statement.setString(4, tvSeries.getGenre());
      statement.setString(5, tvSeries.getStatus());
      statement.setInt(6, tvSeries.getEpCount());
      statement.setInt(7, tvSeries.getSeasonCount());
      statement.setInt(8, tvSeries.getRating());
      statement.setString(9, tvSeries.getType());
      statement.executeUpdate();
    }
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

  // TODO sistemare metodo edit e rimuovere try catch che verranno gestiti nel
  // command

  public void editMedia(Media media) {
    if (media instanceof Movie) {
      Movie movie = (Movie) media;
      String query = "UPDATE movie_table SET "
          + "title = ?, "
          + "director = ?, "
          + "genre = ?, "
          + "status = ?, "
          + "year = ?, "
          + "rating = ? "
          + "WHERE id = ? ";
      try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDirector());
        statement.setString(3, movie.getGenre());
        statement.setString(4, movie.getStatus());
        statement.setInt(5, movie.getYear());
        statement.setInt(6, movie.getRating());
        System.out.println("Sto modificando " + media.getId());
        statement.setInt(7, movie.getId());
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
          System.out.println("Modifica completata");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
    if (media instanceof TvSeries) {
      TvSeries tvSeries = (TvSeries) media;
      String query = "UPDATE movie_table SET "
          + "title = ?, "
          + "director = ?, "
          + "genre = ?, "
          + "status = ?, "
          + "episodes = ?,"
          + "seasons = ?,"
          + "year = ?, "
          + "rating = ? "
          + "WHERE id = ? ";

      try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, tvSeries.getTitle());
        statement.setString(2, tvSeries.getDirector());
        statement.setString(3, tvSeries.getGenre());
        statement.setString(4, tvSeries.getStatus());
        statement.setInt(5, tvSeries.getEpCount());
        statement.setInt(6, tvSeries.getSeasonCount());
        statement.setInt(7, tvSeries.getYear());
        statement.setInt(8, tvSeries.getRating());
        statement.setInt(9, tvSeries.getId());
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
          System.out.println("Modifica completata");
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
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

  public List<Media> getAllMedia() {
    List<Media> mediaList = new ArrayList<>();
    String query = "SELECT * FROM movie_table";
    Map<String, Object> mediaMap;
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        mediaMap = convertRsToMediaMap(rs);
        if (mediaMap.get("type").equals("tvSeries"))
          mediaList.add(new TvSeriesFactory().createFromData(mediaMap));
        else if (mediaMap.get("type").equals("movie"))
          mediaList.add(new MovieFactory().createFromData(mediaMap));
        else
          throw new IllegalArgumentException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
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

  private Map<String, Object> convertRsToMediaMap(ResultSet rs) {
    Map<String, Object> result = new HashMap<>();
    try {
      result.put("id", rs.getInt("id"));
      result.put("title", rs.getString("title"));
      result.put("director", rs.getString("director"));
      result.put("genre", rs.getString("genre"));
      result.put("year", rs.getInt("year"));
      result.put("rating", rs.getInt("rating"));
      result.put("type", rs.getString("type"));

      if (result.get("type").equals("tvseries")) {
        result.put("episodes", rs.getInt("episodes"));
        result.put("seasons", rs.getInt("seasons"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }
}
