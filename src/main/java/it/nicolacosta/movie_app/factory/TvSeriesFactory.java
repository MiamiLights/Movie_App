package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.TvSeries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TvSeriesFactory extends MediaFactory{
    @Override
    public Media createFromResultSet(ResultSet rs) throws SQLException {
        TvSeries tvSeries = new TvSeries();
        populateCommonFields(tvSeries, rs);
        tvSeries.setEpCount(rs.getInt("episodes"));
        tvSeries.setSeasonCount(rs.getInt("seasons"));
        return tvSeries;
    }
}
