package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

public interface Command {

  public void execute() throws SQLException;
  public void undo() throws SQLException;

}
