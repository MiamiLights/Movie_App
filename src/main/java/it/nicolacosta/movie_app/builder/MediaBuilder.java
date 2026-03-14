package it.nicolacosta.movie_app.builder;

import it.nicolacosta.movie_app.model.Media;

// Questa tipizzazione serve a gestire il chaining di comandi
public abstract class MediaBuilder<T extends Media, B extends MediaBuilder<T, B>> {
    protected int id;
    protected String title;
    protected String director;
    protected String genre;
    protected String status;
    protected int year;
    protected int rating;

    // Metodo per mantenere il chaining corretto
    protected abstract B self();

    public B id(int id){ this.id = id; return self();}
    public B title(String title){ this.title = title; return self();}
    public B director(String director){ this.director = director; return self();}
    public B genre(String genre){ this.genre = genre; return self();}
    public B status(String status){ this.status = status; return self();}
    public B year(int year){ this.year = year; return self();}
    public B rating(int rating) { this.rating = rating; return self();}

    public abstract T build();

}
