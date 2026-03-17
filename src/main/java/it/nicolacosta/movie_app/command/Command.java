package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

public interface Command {

  void execute() throws SQLException;
  void undo() throws SQLException;

}
