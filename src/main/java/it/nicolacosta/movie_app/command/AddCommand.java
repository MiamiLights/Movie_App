package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import it.nicolacosta.movie_app.service.MediaService;


public class AddCommand implements Command {

  private final Media mediaToAdd;
  private final MediaService service;

  public AddCommand(Media mediaToAdd, MediaService service) {
    this.mediaToAdd = mediaToAdd;
    this.service = service;
  }

  public void execute() throws SQLException{
      service.addMedia(mediaToAdd);
  }

  @Override
  public void undo() throws SQLException {
    service.deleteMedia(mediaToAdd.getId());
  }

}
