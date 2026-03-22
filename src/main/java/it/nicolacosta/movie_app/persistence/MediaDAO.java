package it.nicolacosta.movie_app.persistence;

import it.nicolacosta.movie_app.model.Media;

import java.util.List;

public interface MediaDAO {

    void addMedia(Media media);
    void deleteMedia(int id);
    void editMedia(Media media);
    List<Media> getAllMedia();
    Media getMedia(int id);

}
