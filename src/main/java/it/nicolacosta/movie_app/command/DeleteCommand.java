package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;

public class DeleteCommand implements Command {

  private final Media media;
  private final MediaDAO dao;

  public DeleteCommand(Media mediaToDelete, MediaDAO dao) {
    this.media = mediaToDelete;
    this.dao = dao;
  }

  public void execute() throws SQLException{
      dao.deleteMedia(media);
  }
}
