package it.nicolacosta.movie_app.model;

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

  public String getDirector() {
    return director;
  }

  public String getGenre() {
    return genre;
  }

  public String getStatus() {
    return status;
  }

  public int getYear() {
    return year;
  }

  public int getRating() {
    return rating;
  }

  public String getType() {
    return this.type;
  }

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
