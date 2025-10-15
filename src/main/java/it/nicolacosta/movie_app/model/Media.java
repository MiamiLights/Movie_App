package it.nicolacosta.movie_app.model;

public abstract class Media {
    protected int id;
    protected String title;
    protected String director;
    protected String genre;
    protected String status;
    protected int year;
    protected int rating;

    public Media(int id, String title, String director, String genre, String status, int year, int rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.status = status;
        this.year = year;
        this.rating = rating;
    }

    public Media(){}

    public int getId() {
        return id;
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
}
