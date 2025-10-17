package it.nicolacosta.movie_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import it.nicolacosta.movie_app.model.Movie;
import it.nicolacosta.movie_app.persistence.MovieDAO;

public class MainPageController implements Initializable {

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private GridPane gridPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    int numColumns = 10;
    for (int i = 0; i < numColumns; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setMinWidth(120);
      colConst.setPercentWidth(100.0 / numColumns);
      gridPane.getColumnConstraints().add(colConst);
    }

    dbCalls();
    int numRows = 2;
    for (int i = 0; i < numRows; i++) {
      RowConstraints rowConst = new RowConstraints();
      rowConst.setMinHeight(120);
      rowConst.setVgrow(Priority.SOMETIMES);
      gridPane.getRowConstraints().add(rowConst);
      Label label = new Label("Contenuto riga " + (i + 1));
      gridPane.add(label, i, 0);
    }
  }

  private void dbCalls() {
    try {
      MovieDAO movieDAO = new MovieDAO();
      movieDAO.deleteMovie(11);
      Movie movie = new Movie("Il gladiatore333443434", null, "azione", null, 2003, 5);
      movie.setId(12);
      movieDAO.editMedia(movie);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
