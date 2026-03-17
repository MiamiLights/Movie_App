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

    public void completeStatement(Media media, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, media.getTitle());
        stmt.setString(2, media.getDirector());
        stmt.setString(3, media.getGenre());
        stmt.setString(4, media.getStatus());
        if (media instanceof Movie) {
            stmt.setObject(5, Types.NULL);
            stmt.setObject(6, Types.NULL);
        }
        if (media instanceof TvSeries){
            stmt.setInt(5, ((TvSeries) media).getEpCount());
            stmt.setInt(6, ((TvSeries) media).getSeasonCount());
        }
        stmt.setInt(7, media.getYear());
        stmt.setInt(8, media.getRating());
        stmt.setString(9, media.getType());
    }
}
