package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;

/**
 * AddCommand
 */
public class AddCommand implements Command {

  private final Media mediaToAdd;
  private final MediaDAO dao;

  public AddCommand(Media mediaToAdd, MediaDAO dao) {
    this.mediaToAdd = mediaToAdd;
    this.dao = dao;
  }

  public void execute() throws SQLException{
      dao.addMedia(mediaToAdd);
  }

}
