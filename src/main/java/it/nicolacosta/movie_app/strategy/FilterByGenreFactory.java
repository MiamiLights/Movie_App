package it.nicolacosta.movie_app.strategy;

import java.util.List;
import java.util.stream.Collectors;

import it.nicolacosta.movie_app.model.Media;

/**
 * FilterByGenreFactory
 */
public class FilterByGenreFactory implements FilterStrategy {

  private final String genre;

  public FilterByGenreFactory(String genre) {
    this.genre = genre;
  }

  public List<Media> filter(List<Media> allMedia) {
    return allMedia.stream()
        .filter(media -> media.getGenre() == this.genre)
        .collect(Collectors.toList());
  }
}
