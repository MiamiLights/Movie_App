package it.nicolacosta.movie_app.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Movie extends Media {

  public Movie(int id, String title, String director, String genre, String status, int year, int rating) {
    super(title, director, genre, status, year, rating);
    type = "Film";
    this.id = id;
  }

  public Movie() {
    this.type = "Film";
  }


  @Override
  public String toString() {
    return "Movie -> " + super.toString();
  }

}
