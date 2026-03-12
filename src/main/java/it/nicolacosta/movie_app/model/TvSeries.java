package it.nicolacosta.movie_app.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TvSeries extends Media {
  private int epCount;
  private int seasonCount;

  public TvSeries(int id, String title, String director, String genre, String status, int year, int rating, int epCount,
      int seasonCount) {
    super(title, director, genre, status, year, rating);
    this.id = id;
    this.epCount = epCount;
    this.seasonCount = seasonCount;
    this.type = "tvseries";
  }

  public TvSeries() {
    this.type = "tvseries";
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
