package it.nicolacosta.movie_app.model;

public class Movie extends Media {

    public Movie(int id, String title, String director, String genre, String status, int year, int rating) {
        super(id, title, director, genre, status, year, rating);
    }

    public Movie(){}

}
