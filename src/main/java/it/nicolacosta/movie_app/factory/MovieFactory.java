package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;

import java.sql.SQLException;
import java.util.Map;

public class MovieFactory extends MediaFactory {

  @Override
  public Media createFromData(Map<String, Object> data) throws SQLException {
    Movie movie = new Movie();
    populateCommonFields(movie, data);
    return movie;
  }
}
