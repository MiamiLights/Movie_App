package it.nicolacosta.movie_app.command;

import java.sql.SQLException;

/**
 * Command
 */
public interface Command {

  public void execute() throws SQLException;

}
