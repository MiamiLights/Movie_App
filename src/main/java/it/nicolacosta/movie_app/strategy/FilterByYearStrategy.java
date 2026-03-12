package it.nicolacosta.movie_app.strategy;

import java.util.List;
import java.util.stream.Collectors;

import it.nicolacosta.movie_app.model.Media;

public class FilterByYearStrategy implements FilterStrategy {

  private final int year;

  public FilterByYearStrategy(int year) {
    this.year = year;
  }

  public List<Media> filter(List<Media> allMedia) {
    return allMedia.stream()
        .filter(media -> media.getYear() == this.year)
        .collect(Collectors.toList());
  }
}
