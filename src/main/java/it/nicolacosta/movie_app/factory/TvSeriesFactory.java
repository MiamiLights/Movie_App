package it.nicolacosta.movie_app.factory;

import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.TvSeries;
import java.sql.SQLException;
import java.util.Map;

public class TvSeriesFactory extends MediaFactory {
  @Override
  public Media createFromData(Map<String, Object> data) throws SQLException {
    TvSeries tvSeries = new TvSeries();
    populateCommonFields(tvSeries, data);
    tvSeries.setEpCount((Integer) data.get("episodes"));
    tvSeries.setSeasonCount((Integer) data.get("seasons"));
    return tvSeries;
  }
}
