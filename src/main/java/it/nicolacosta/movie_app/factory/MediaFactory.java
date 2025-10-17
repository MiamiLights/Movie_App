package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MediaFactory {

  public abstract Media createFromResultSet(ResultSet rs) throws SQLException;

  protected void populateCommonFields(Media media, ResultSet rs) throws SQLException {
    media.setId(rs.getInt("id"));
    media.setTitle(rs.getString("title"));
    media.setDirector(rs.getString("director"));
    media.setGenre(rs.getString("genre"));
    media.setStatus(rs.getString("status"));
    media.setYear(rs.getInt("year"));
    media.setRating(rs.getInt("rating"));
  }

}
