package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import java.sql.SQLException;
import java.util.Map;

public abstract class MediaFactory {

  public abstract Media createFromData(Map<String, Object> data) throws SQLException;

  protected void populateCommonFields(Media media, Map<String, Object> data) throws SQLException {
    media.setId((Integer) data.get("id"));
    media.setTitle((String) data.get("title"));
    media.setDirector((String) data.get("director"));
    media.setGenre((String) data.get("genre"));
    media.setStatus((String) data.get("status"));
    media.setYear((Integer) data.get("year"));
    media.setRating((Integer) data.get("rating"));
  }
}
