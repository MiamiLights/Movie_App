package it.nicolacosta.movie_app.command;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.service.MediaService;


public class AddCommand implements Command {

  private final Media mediaToAdd;
  private final MediaService service;

  public AddCommand(Media mediaToAdd, MediaService service) {
    this.mediaToAdd = mediaToAdd;
    this.service = service;
  }

  public void execute(){
      service.addMedia(mediaToAdd);
    System.out.println("AddCommand ESEGUITO: Il DB ha assegnato l'ID -> " + mediaToAdd.getId());
  }

  @Override
  public void undo(){
    System.out.println("AddCommand UNDO: Tento di eliminare l'ID -> " + mediaToAdd.getId());
    service.deleteMedia(mediaToAdd.getId());
  }

}
