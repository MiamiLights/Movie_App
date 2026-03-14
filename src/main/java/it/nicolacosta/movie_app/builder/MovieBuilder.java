package it.nicolacosta.movie_app.builder;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;

public class MovieBuilder extends MediaBuilder<Movie, MovieBuilder> {

    @Override
    protected MovieBuilder self() {
        return this;
    }

    @Override
    public Movie build() {
        return new Movie(id, title, director, genre, status, year, rating);
    }
}
