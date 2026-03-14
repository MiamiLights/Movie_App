package it.nicolacosta.movie_app.builder;

import it.nicolacosta.movie_app.model.TvSeries;

public class TvSeriesBuilder extends MediaBuilder<TvSeries, TvSeriesBuilder> {

    protected int episodes;
    protected int seasons;

    public TvSeriesBuilder episodes(int episodes){ this.episodes = episodes; return self();}
    public TvSeriesBuilder seasons(int seasons){ this.seasons = seasons; return self();}

    @Override
    protected TvSeriesBuilder self() {
        return this;
    }

    @Override
    public TvSeries build() {
        return new TvSeries(id, title, director, genre, status, year, rating, episodes, seasons);
    }
}
