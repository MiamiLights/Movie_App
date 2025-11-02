package it.nicolacosta.movie_app.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TvSeries extends Media {
  private int epCount;
  private int seasonCount;

  public TvSeries(int id, String title, String director, String genre, String status, int year, int rating, int epCount,
      int seasonCount) {
    super(title, director, genre, status, year, rating);
    this.epCount = epCount;
    this.seasonCount = seasonCount;
    type = "tvseries";
  }

  public TvSeries() {
  }

  @Override
  public String getInsertColumnNames() {
    return "title, director, year, genre, status, episodes, seasons, rating, type";
  }

  @Override
  public String getInsertValuePlaceholders() {
    return "?, ?, ?, ?, ?, ?, ?, ?, ?";
  }

  @Override
  public void setInsertStatementParams(PreparedStatement statement) throws SQLException {
    statement.setString(1, getTitle());
    statement.setString(2, getDirector());
    statement.setInt(3, getYear());
    statement.setString(4, getGenre());
    statement.setString(5, getStatus());
    statement.setInt(6, getEpCount());
    statement.setInt(7, getSeasonCount());
    statement.setInt(8, getRating());
    statement.setString(9, getType());
  }

  @Override
  public int setUpdateQueryParams(PreparedStatement statement, int paramIndex) throws SQLException {
    statement.setString(paramIndex++, getTitle());
    statement.setString(paramIndex++, getDirector());
    statement.setString(paramIndex++, getGenre());
    statement.setString(paramIndex++, getStatus());
    statement.setInt(paramIndex++, getYear());
    statement.setInt(paramIndex++, getRating());
    statement.setInt(paramIndex++, getSeasonCount());
    statement.setInt(paramIndex++, getEpCount());
    return paramIndex;
  }

  @Override
  public String getUpdateQuerySetters() {
    return "title = ?, director = ?, genre = ?, status = ?, year = ?, rating = ?, seasons = ?, episodes = ?";

  }

  public int getEpCount() {
    return epCount;
  }

  public void setEpCount(int epCount) {
    this.epCount = epCount;
  }

  public int getSeasonCount() {
    return seasonCount;
  }

  public void setSeasonCount(int seasonCount) {
    this.seasonCount = seasonCount;
  }

  @Override
  public String toString() {
    return "TvSeries -> " + super.toString() +
            " {episodes=" + epCount +
            ", seasons=" + seasonCount + "}";
  }
}
