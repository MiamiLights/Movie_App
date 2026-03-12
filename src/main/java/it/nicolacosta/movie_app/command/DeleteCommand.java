package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import it.nicolacosta.movie_app.service.MediaService;

public class DeleteCommand implements Command {

  private final Media mediaToDelete;
  private final MediaService service;

  public DeleteCommand(Media mediaToDelete, MediaService service) {
    this.mediaToDelete = mediaToDelete;
    this.service = service;
  }

  public void execute() throws SQLException{
      service.deleteMedia(mediaToDelete.getId());
  }

  @Override
  public void undo() throws SQLException {
    service.addMedia(mediaToDelete);
  }
}
