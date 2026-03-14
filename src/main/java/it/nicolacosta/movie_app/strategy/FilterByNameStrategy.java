package it.nicolacosta.movie_app.strategy;

import it.nicolacosta.movie_app.model.Media;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByNameStrategy implements FilterStrategy{

    private String name;

    public FilterByNameStrategy(String name){
        this.name = name;
    }

    @Override
    public List<Media> filter(List<Media> allMedia) {
        return allMedia.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
