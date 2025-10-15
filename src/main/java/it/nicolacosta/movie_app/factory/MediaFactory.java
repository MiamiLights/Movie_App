package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;
import it.nicolacosta.movie_app.model.TvSeries;

public class MediaFactory {

    public static Media createMedia(String type){

        if ("MOVIE".equalsIgnoreCase(type))
            return new Movie();
        if ("TVSERIES".equalsIgnoreCase(type))
            return new TvSeries();

        throw new IllegalArgumentException("Tipo di media sconosciuto " + type);
    }
}
