package it.nicolacosta.movie_app.command;

import java.util.ArrayList;
import java.util.List;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MovieDAO;

/**
 * GetAllCommand
 */
public class GetAllCommand implements Command {

  private List<Media> allMedia;
  private final MovieDAO dao;

  public GetAllCommand(MovieDAO dao) {
    this.dao = dao;
    allMedia = new ArrayList<>();
  }

  public void execute() {
    allMedia = dao.getAllMedia();
  }

  public List<Media> getAllMedia() {
    return allMedia;
  }

}
