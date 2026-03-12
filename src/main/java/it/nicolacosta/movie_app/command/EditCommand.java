package it.nicolacosta.movie_app.command;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.service.MediaService;

import java.sql.SQLException;

public class EditCommand implements Command {

    private final Media mediaToEdit;
    private final MediaService service;
    private Media oldMedia;

    public EditCommand(Media mediaToEdit, MediaService mediaService){
        this.mediaToEdit = mediaToEdit;
        this.service = mediaService;
    }

    @Override
    public void execute() throws SQLException{
        this.oldMedia = service.getMedia(mediaToEdit.getId());
        service.editMedia(mediaToEdit);

    }

    @Override
    public void undo() throws SQLException {
        service.editMedia(oldMedia);
    }
}
