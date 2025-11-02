package it.nicolacosta.movie_app.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;

/**
 * GetAllCommand
 */
public class GetAllCommand implements Command {

  private List<Media> allMedia;
  private final MediaDAO dao;

  public GetAllCommand(MediaDAO dao) {
    this.dao = dao;
    allMedia = new ArrayList<>();
  }

  public void execute()throws SQLException {
    allMedia = dao.getAllMedia();
  }

  public List<Media> getAllMedia() throws SQLException {
    execute();
    return allMedia;
  }

}
