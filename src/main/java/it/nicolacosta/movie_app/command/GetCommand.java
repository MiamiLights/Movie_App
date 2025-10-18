package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MovieDAO;

/**
 * GetCommand
 */
public class GetCommand {

  private final Media mediaToGet;
  private final MovieDAO dao;

  public GetCommand(Media mediaToGet, MovieDAO dao) {
    this.mediaToGet = mediaToGet;
    this.dao = dao;
  }

  public void execute(Media media) {
    try {
      dao.getMedia(mediaToGet);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Media getMedia() {
    return mediaToGet;
  }
}
