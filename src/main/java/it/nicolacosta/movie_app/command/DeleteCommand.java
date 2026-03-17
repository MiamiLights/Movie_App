package it.nicolacosta.movie_app.command;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.service.MediaService;

public class DeleteCommand implements Command {

  private final Media mediaToDelete;
  private final MediaService service;

  public DeleteCommand(Media mediaToDelete, MediaService service) {
    this.mediaToDelete = mediaToDelete;
    this.service = service;
  }

  public void execute() {
      service.deleteMedia(mediaToDelete.getId());
  }

  @Override
  public void undo(){
    service.addMedia(mediaToDelete);
  }
}
