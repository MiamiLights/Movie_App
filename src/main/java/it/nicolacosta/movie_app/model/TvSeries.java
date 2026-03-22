package it.nicolacosta.movie_app.model;


public class TvSeries extends Media {
  private int epCount;
  private int seasonCount;

  public TvSeries(
          int id, String title, String director, String genre,
          String status, int year, int rating,
          int epCount, int seasonCount) {

    super(title, director, genre, status, year, rating);
    this.id = id;
    this.epCount = epCount;
    this.seasonCount = seasonCount;
    this.type = "Serie TV";
  }

  public TvSeries() {
    this.type = "Serie TV";
  }

  public int getEpCount() {
    return epCount;
  }

  public int getSeasonCount() {
    return seasonCount;
  }

  @Override
  public String toString() {
    return "TvSeries -> " + super.toString() +
            " {episodes=" + epCount +
            ", seasons=" + seasonCount + "}";
  }
}

