package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;

/**
 * GetCommand
 */
public class GetCommand {

  private final Media mediaToGet;
  private final MediaDAO dao;

  public GetCommand(Media mediaToGet, MediaDAO dao) {
    this.mediaToGet = mediaToGet;
    this.dao = dao;
  }

  public void execute(Media media) throws SQLException {
      dao.getMedia(mediaToGet);
  }

  public Media getMedia() {
    return mediaToGet;
  }
}
