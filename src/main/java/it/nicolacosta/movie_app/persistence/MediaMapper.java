package it.nicolacosta.movie_app.persistence;

import it.nicolacosta.movie_app.factory.MovieFactory;
import it.nicolacosta.movie_app.factory.TvSeriesFactory;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.TvSeries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MediaMapper {
    private final MovieFactory movieFactory = new MovieFactory();
    private final TvSeriesFactory tvSeriesFactory = new TvSeriesFactory();

    public Map<String, Object> mediaToMap(Media media) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", media.getTitle());
        data.put("director", media.getDirector());
        data.put("genre", media.getGenre());
        data.put("status", media.getStatus());
        data.put("year", media.getYear());
        data.put("rating", media.getRating());
        data.put("type", media.getType());

        if (media instanceof TvSeries) {
            TvSeries tv = (TvSeries) media;
            data.put("episodes", tv.getEpCount());
            data.put("seasons", tv.getSeasonCount());
        } else {
            data.put("episodes", null);
            data.put("seasons", null);
        }
        return data;
    }

    public Media resultSetToMedia(ResultSet rs) throws SQLException {
        Map<String, Object> mediaMap = new HashMap<>();
        mediaMap.put("id", rs.getInt("id"));
        mediaMap.put("title", rs.getString("title"));
        mediaMap.put("director", rs.getString("director"));
        mediaMap.put("genre", rs.getString("genre"));
        mediaMap.put("status", rs.getString("status"));
        mediaMap.put("year", rs.getInt("year"));
        mediaMap.put("rating", rs.getInt("rating"));

        String type = rs.getString("type");
        mediaMap.put("type", type);

        if ("tvSeries".equalsIgnoreCase(type)) {
            mediaMap.put("episodes", rs.getInt("episodes"));
            mediaMap.put("seasons", rs.getInt("seasons"));
            return tvSeriesFactory.createFromData(mediaMap);
        } else if ("movie".equalsIgnoreCase(type)) {
            return movieFactory.createFromData(mediaMap);
        } else {
            throw new IllegalArgumentException("Tipo media sconosciuto: " + type);
        }
    }
}
