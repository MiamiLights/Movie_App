package it.nicolacosta.movie_app.strategy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import it.nicolacosta.movie_app.model.Media;

public class FilterByGenreStrategy implements FilterStrategy {

  private final String genre;

  public FilterByGenreStrategy(String genre) {
    this.genre = genre;
  }

  public List<Media> filter(List<Media> allMedia) {
    return allMedia.stream()
        .filter(media -> Objects.equals(media.getGenre(), this.genre))
        .collect(Collectors.toList());
  }
}
