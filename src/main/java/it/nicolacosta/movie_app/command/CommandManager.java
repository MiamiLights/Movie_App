package it.nicolacosta.movie_app.command;

import java.sql.SQLException;
import java.util.Stack;

public class CommandManager {
    private final Stack<Command> history = new Stack<>();

    public void executeCommand(Command cmd) throws SQLException{
        cmd.execute();
        history.push(cmd);
    }

    public void undo() throws SQLException{
        if (!history.isEmpty()){
            Command cmd = history.pop();
            cmd.undo();
        }
    }
}

