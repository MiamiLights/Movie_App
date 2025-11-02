package it.nicolacosta.movie_app.command;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;

import java.sql.SQLException;

public class EditCommand implements Command {

    private final Media mediaToEdit;
    private final MediaDAO mediaDAO;

    public EditCommand(Media mediaToEdit, MediaDAO mediaDAO){
        this.mediaToEdit = mediaToEdit;
        this.mediaDAO = mediaDAO;
    }

    @Override
    public void execute() throws SQLException{
        mediaDAO.editMedia(mediaToEdit);

    }
}
