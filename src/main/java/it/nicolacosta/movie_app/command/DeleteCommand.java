package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MovieDAO;

public class DeleteCommand implements Command {

  private final Media media;
  private final MovieDAO dao;

  public DeleteCommand(Media mediaToDelete, MovieDAO dao) {
    this.media = mediaToDelete;
    this.dao = dao;
  }

  public void execute() {
    try {
      dao.deleteMedia(media);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
