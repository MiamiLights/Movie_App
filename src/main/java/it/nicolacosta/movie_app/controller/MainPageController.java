package it.nicolacosta.movie_app.controller;

import it.nicolacosta.movie_app.persistence.DatabaseConnectionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
}