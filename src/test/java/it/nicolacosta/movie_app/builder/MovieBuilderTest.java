package it.nicolacosta.movie_app.builder;

import it.nicolacosta.movie_app.model.Movie;
import it.nicolacosta.movie_app.model.TvSeries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieBuilderTest {


    @Test
    void build_shouldCreateMovieWithCorrectParams(){
        Movie movie = new MovieBuilder()
                .title("Interstellar")
                .director("Nolan")
                .year(2014)
                .build();

        assertEquals("Interstellar", movie.getTitle());
        assertEquals("Nolan", movie.getDirector());
        assertEquals(2014, movie.getYear());
        assertNull(movie.getGenre());
        assertEquals(0, movie.getRating());
    }

    @Test
    void build_shouldCreateTvSeriesWithCorrectParams(){
        TvSeries tvSeries = new TvSeriesBuilder()
                .title("Peaky Blinders")
                .year(2018)
                .episodes(48)
                .seasons(4)
                .build();

        assertEquals("Peaky Blinders", tvSeries.getTitle());
        assertEquals(2018, tvSeries.getYear());
        assertNull(tvSeries.getGenre());
        assertEquals(0, tvSeries.getRating());
        assertEquals(48, tvSeries.getEpCount());
        assertEquals(4, tvSeries.getSeasonCount());
        assertNull(tvSeries.getStatus());
    }

}