package it.nicolacosta.movie_app.model;

public class Movie extends Media {

  public Movie(String title, String director, String genre, String status, int year, int rating) {
    super(title, director, genre, status, year, rating);
    type = "movie";
  }

  public Movie() {
  }

}
