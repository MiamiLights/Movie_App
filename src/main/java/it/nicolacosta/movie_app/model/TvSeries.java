package it.nicolacosta.movie_app.model;

public class TvSeries extends Media{
    private int epCount;
    private int seasonCount;

    public TvSeries(int id, String title, String director, String genre, String status, int year, int rating, int epCount, int seasonCount) {
        super(id, title, director, genre, status, year, rating);
        this.epCount = epCount;
        this.seasonCount = seasonCount;
    }

    public TvSeries(){}

    public int getEpCount() {
        return epCount;
    }

    public void setEpCount(int epCount) {
        this.epCount = epCount;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(int seasonCount) {
        this.seasonCount = seasonCount;
    }
}
