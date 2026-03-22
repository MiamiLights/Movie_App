package it.nicolacosta.movie_app.builder;

import it.nicolacosta.movie_app.model.Media;

public class MediaDirector {

    private MediaBuilder<?, ?> getBuilder(MediaDTO dto){
        if("Serie TV".equalsIgnoreCase(dto.type)) {
            return new TvSeriesBuilder()
                    .seasons(dto.seasons)
                    .episodes(dto.episodes);
        } else if ("Film".equalsIgnoreCase(dto.type)) {
            return new MovieBuilder();
        }
        throw new IllegalArgumentException("Genre non supportato: " + dto.genre);
    }

    public Media buildMedia(MediaDTO dto){
        return getBuilder(dto)
                .title(dto.title)
                .director(dto.director)
                .genre(dto.genre)
                .status(dto.status)
                .year(dto.year)
                .rating(dto.rating)
                .build();
    }

}
