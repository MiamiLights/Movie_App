package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MovieDAO;

/**
 * AddCommand
 */
public class AddCommand implements Command {

  private final Media mediaToAdd;
  private final MovieDAO dao;

  public AddCommand(Media mediaToAdd, MovieDAO dao) {
    this.mediaToAdd = mediaToAdd;
    this.dao = dao;
  }

  public void execute() {
    try {
      dao.addMedia(mediaToAdd);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
