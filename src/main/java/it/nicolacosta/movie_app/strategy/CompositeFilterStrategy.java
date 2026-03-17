package it.nicolacosta.movie_app.strategy;

import it.nicolacosta.movie_app.model.Media;

import java.util.ArrayList;
import java.util.List;

public class CompositeFilterStrategy implements FilterStrategy{


    private final List<FilterStrategy> strategies = new ArrayList<>();

    public void addStrategy(FilterStrategy strategy){
        strategies.add(strategy);
    }


    @Override
    public List<Media> filter(List<Media> allMedia) {
        //Context del pattern strategy
        List<Media> filtered = allMedia;
        for (FilterStrategy strategy : strategies){
            filtered = strategy.filter(filtered);
        }
        return filtered;
    }
}
