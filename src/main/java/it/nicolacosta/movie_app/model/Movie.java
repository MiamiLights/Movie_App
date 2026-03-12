package it.nicolacosta.movie_app.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Movie extends Media {

  public Movie(String title, String director, String genre, String status, int year, int rating) {
    super(title, director, genre, status, year, rating);
    type = "movie";
  }

  public Movie() {
    this.type = "movie";
  }


  @Override
  public String toString() {
    return "Movie -> " + super.toString();
  }

}
