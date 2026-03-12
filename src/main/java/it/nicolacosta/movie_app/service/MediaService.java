package it.nicolacosta.movie_app.service;

import it.nicolacosta.movie_app.events.EventDispatcher;
import it.nicolacosta.movie_app.events.Observer;
import it.nicolacosta.movie_app.events.Subject;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaService {
    private final MediaDAO mediaDAO;
    private final EventDispatcher eventDispatcher;

    public MediaService(MediaDAO mediaDAO, EventDispatcher eventDispatcher) throws SQLException{
        this.mediaDAO = mediaDAO;
        this.eventDispatcher = eventDispatcher;
    }

    public void addMedia(Media media){
        try{
            mediaDAO.addMedia(media);
            eventDispatcher.notifyObservers();
        }catch (SQLException e){
            System.err.println("Errore durante il salvataggio" + e.getMessage());
        }
    }

    public void editMedia(Media media) {
        try {
            mediaDAO.editMedia(media);
            eventDispatcher.notifyObservers();
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio" + e.getMessage());
        }
    }

    public void deleteMedia(int id) {
        try {
            mediaDAO.deleteMedia(id);
            eventDispatcher.notifyObservers();
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione" + e.getMessage());
        }
    }

    public Media getMedia(int id) {
        try {
            return mediaDAO.getMedia(id);
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento" + e.getMessage());
        }
        return null;
    }

    public List<Media> getAllMedia() {
        try {
            return mediaDAO.getAllMedia();
        } catch (SQLException e) {
            System.err.println("Errore durante l'ottenimento" + e.getMessage());
        }
        return null;
    }

}
