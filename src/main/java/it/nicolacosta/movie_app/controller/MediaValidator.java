package it.nicolacosta.movie_app.controller;

import it.nicolacosta.movie_app.builder.MediaDTO;

public class MediaValidator {
    public static void validate(MediaDTO dto) throws IllegalArgumentException {
        if (dto.getTitle().isBlank() ||
                dto.getDirector().isBlank() ||
                dto.getStatus() == null ||
                dto.getType() == null) {
            throw new IllegalArgumentException("Compila tutti i campi obbligatori.");
        }
    }
}
