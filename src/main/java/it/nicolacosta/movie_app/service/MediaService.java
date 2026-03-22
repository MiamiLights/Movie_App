package it.nicolacosta.movie_app.service;

import it.nicolacosta.movie_app.events.EventDispatcher;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import it.nicolacosta.movie_app.strategy.*;


import java.util.ArrayList;
import java.util.List;

public class MediaService {
    private final MediaDAO mediaDAO;
    private final EventDispatcher eventDispatcher;

    public MediaService(MediaDAO mediaDAO, EventDispatcher eventDispatcher) {
        this.mediaDAO = mediaDAO;
        this.eventDispatcher = eventDispatcher;
    }

    public void addMedia(Media media){
        mediaDAO.addMedia(media);
        eventDispatcher.notifyObservers();

    }

    public void editMedia(Media media) {
        mediaDAO.editMedia(media);
        eventDispatcher.notifyObservers();
    }

    public void deleteMedia(int id) {
        mediaDAO.deleteMedia(id);
        eventDispatcher.notifyObservers();

    }

    public Media getMedia(int id) {
        return mediaDAO.getMedia(id);
    }

    public List<Media> getAllMedia() {
        return mediaDAO.getAllMedia();
    }

    public List<Media> filterData(FilterStrategy strategy) {
        List<Media> allMedia = getAllMedia();
        if (allMedia == null) return new ArrayList<>();

        return strategy.filter(allMedia);
    }

}
