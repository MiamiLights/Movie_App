package it.nicolacosta.movie_app.strategy;

import it.nicolacosta.movie_app.model.Media;

import java.util.List;

public interface FilterStrategy {
    List<Media> filter (List<Media> allMedia);
}
