package it.nicolacosta.movie_app.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Media {
  protected int id;
  protected String title;
  protected String director;
  protected String genre;
  protected String status;
  protected int year;
  protected int rating;
  protected String type;

  public Media(String title, String director, String genre, String status, int year, int rating) {
    this.title = title;
    this.director = director;
    this.genre = genre;
    this.status = status;
    this.year = year;
    this.rating = rating;
  }

  public Media() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getType() {
    return this.type;
  }

  public abstract String getInsertColumnNames();
  public abstract String getInsertValuePlaceholders();
  public abstract void setInsertStatementParams(PreparedStatement statement) throws SQLException;
  public abstract int setUpdateQueryParams(PreparedStatement statement, int paramIndex) throws SQLException;
  public abstract String getUpdateQuerySetters();

  @Override
  public String toString() {
    return "Media{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", director='" + director + '\'' +
            ", genre='" + genre + '\'' +
            ", status='" + status + '\'' +
            ", year=" + year +
            ", rating=" + rating +
            ", type='" + type + '\'' +
            '}';
  }
}
