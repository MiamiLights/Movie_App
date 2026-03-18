package it.nicolacosta.movie_app.persistence;

import it.nicolacosta.movie_app.builder.MediaBuilder;
import it.nicolacosta.movie_app.builder.MovieBuilder;
import it.nicolacosta.movie_app.builder.TvSeriesBuilder;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;
import it.nicolacosta.movie_app.model.TvSeries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class MediaMapper {

    public Media resultSetToMedia(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        MediaBuilder<?,?> builder;

        if (type.equalsIgnoreCase("Serie TV")){
            builder = new TvSeriesBuilder()
                    .episodes(rs.getInt("episodes"))
                    .seasons(rs.getInt("seasons"));
        }
        else if (type.equalsIgnoreCase("Film")) {
            builder = new MovieBuilder();
        }
        else {
            throw new IllegalArgumentException("Tipo di media sconosciuto: " + type);
        }

        return builder
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .director(rs.getString("director"))
                .genre(rs.getString("genre"))
                .status(rs.getString("status"))
                .year(rs.getInt("year"))
                .rating(rs.getInt("rating"))
                .build();
        
    }

    public int completeStatement(Media media, PreparedStatement stmt) throws SQLException {
        int i = 1;
        stmt.setString(i++, media.getTitle());
        stmt.setString(i++, media.getDirector());
        stmt.setString(i++, media.getGenre());
        stmt.setString(i++, media.getStatus());
        if (media instanceof Movie) {
            stmt.setObject(i++, Types.NULL);
            stmt.setObject(i++, Types.NULL);
        }
        else if (media instanceof TvSeries){
            stmt.setInt(i++, ((TvSeries) media).getEpCount());
            stmt.setInt(i++, ((TvSeries) media).getSeasonCount());
        }
        stmt.setInt(i++, media.getYear());
        stmt.setInt(i++, media.getRating());
        stmt.setString(i++, media.getType());
        return i;
    }
}
