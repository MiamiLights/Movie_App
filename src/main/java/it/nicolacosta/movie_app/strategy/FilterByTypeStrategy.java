package it.nicolacosta.movie_app.strategy;

import it.nicolacosta.movie_app.model.Media;

import java.util.List;

public class FilterByTypeStrategy implements FilterStrategy{
    @Override
    public List<Media> filter(List<Media> allMedia) {
        return List.of();
    }
}
