package it.nicolacosta.movie_app.builder;

public class MediaDTO {
    protected String type, title, director, genre, status;
    protected int year, rating;
    protected Integer seasons, episodes;

    public MediaDTO withTitle(String title){
        this.title = title; return this;
    }
    public MediaDTO withDirector(String director){
        this.director = director; return this;
    }
    public MediaDTO withGenre(String genre){
        this.genre = genre; return this;
    }
    public MediaDTO withStatus(String status){
        this.status = status; return this;
    }
    public MediaDTO withYear(int year){
        this.year = year; return this;
    }
    public MediaDTO withRating(int rating){
        this.rating = rating; return this;
    }
    public MediaDTO withSeasons(int seasons){
        this.seasons = seasons; return this;
    }
    public MediaDTO withEpisodes(int episodes){
        this.episodes = episodes; return this;
    }
    public MediaDTO withType(String type){
        this.type = type; return this;
    }
}
