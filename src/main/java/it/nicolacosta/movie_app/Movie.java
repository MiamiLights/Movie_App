package it.nicolacosta.movie_app;

public class Movie {
    private final String title;
    private final String director;
    private final int year;
    private final String genre;
    private final String status;
    private final int rating;

    public Movie(String title, String director, String genre, String status, int year, int rating){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.status = status;
        this.year = year;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getStatus() {
        return status;
    }

    public int getRating() {
        return rating;
    }
}
