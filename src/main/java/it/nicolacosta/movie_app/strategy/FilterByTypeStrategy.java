package it.nicolacosta.movie_app.strategy;

import it.nicolacosta.movie_app.model.Media;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByTypeStrategy implements FilterStrategy {

  private final Class<? extends Media> targetType;

  public FilterByTypeStrategy(Class<? extends Media> targetType) {
    if (targetType == null) {
      throw new IllegalArgumentException("Il tipo di destinazione non può essere nullo.");
    }
    this.targetType = targetType;
  }

  @Override
  public List<Media> filter(List<Media> allMedia) {
    return allMedia.stream()
        .filter(targetType::isInstance)
        .collect(Collectors.toList());
  }
}
