package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieFactory extends MediaFactory {

    public Media createFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        populateCommonFields(movie, rs);
        return movie;
    }
}
