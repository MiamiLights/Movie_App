package it.nicolacosta.movie_app.persistence;

import it.nicolacosta.movie_app.factory.MediaFactory;
import it.nicolacosta.movie_app.factory.MovieFactory;
import it.nicolacosta.movie_app.factory.TvSeriesFactory;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;
import it.nicolacosta.movie_app.model.TvSeries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

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

  public void deleteMovie(int id) throws SQLException {
    String query = "DELETE FROM movie_table WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, id);
    int rowsAffected = statement.executeUpdate();

    if (rowsAffected > 0) {
      System.out.println("Cancellazione completata");
    }
  }

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

  public Media getMedia(int id) {
    String query = "SELECT * FROM movie_table WHERE id = ?";
    MediaFactory factory = null;
    Media media = null;
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        String type = rs.getString("type");
        if (type.equalsIgnoreCase("movie"))
          factory = new MovieFactory();
        else if (type.equalsIgnoreCase("tvseries"))
          factory = new TvSeriesFactory();
        else
          throw new IllegalArgumentException();
      }
      media = factory.createFromResultSet(rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return media;
  }

  public List<Media> getAllMedia() {
    List<Media> mediaList = new ArrayList<>();
    String query = "SELECT * FROM movie_table";
    try {
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        String type = rs.getString("type");
        MediaFactory factory;
        if (type.equalsIgnoreCase("movie"))
          factory = new MovieFactory();
        else if (type.equalsIgnoreCase("tvseries"))
          factory = new TvSeriesFactory();
        else
          continue;

        Media media = factory.createFromResultSet(rs);
        mediaList.add(media);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mediaList;
  }
}
