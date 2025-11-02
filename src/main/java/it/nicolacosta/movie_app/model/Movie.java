package it.nicolacosta.movie_app.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Movie extends Media {

  public Movie(String title, String director, String genre, String status, int year, int rating) {
    super(title, director, genre, status, year, rating);
    type = "movie";
  }

  public Movie() {
  }

  @Override
  public String getInsertColumnNames() {
    return "title, director, year, genre, status, rating, type";
  }

  @Override
  public String getInsertValuePlaceholders() {
    return "?, ?, ?, ?, ?, ?, ?";
  }

  @Override
  public void setInsertStatementParams(PreparedStatement statement) throws SQLException {
    statement.setString(1, getTitle());
    statement.setString(2, getDirector());
    statement.setInt(3, getYear());
    statement.setString(4, getGenre());
    statement.setString(5, getStatus());
    statement.setInt(6, getRating());
    statement.setString(7, getType());
  }

  @Override
  public int setUpdateQueryParams(PreparedStatement statement, int paramIndex) throws SQLException {
    statement.setString(paramIndex++, getTitle());
    statement.setString(paramIndex++, getDirector());
    statement.setString(paramIndex++, getGenre());
    statement.setString(paramIndex++, getStatus());
    statement.setInt(paramIndex++, getYear());
    statement.setInt(paramIndex++, getRating());
    return paramIndex;
  }

  @Override
  public String getUpdateQuerySetters() {
    return "title = ?, director = ?, genre = ?, status = ?, year = ?, rating = ?";
  }

  @Override
  public String toString() {
    return "Movie -> " + super.toString();
  }

}
