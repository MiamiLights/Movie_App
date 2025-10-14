package it.nicolacosta.movie_app;

import it.nicolacosta.movie_app.DBConnection.DatabaseConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainPageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {

        DatabaseConnectionManager db = new DatabaseConnectionManager();
        Connection conn = db.getConnection();
        System.out.println("Db funzionante?");
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM movie_db");
    }
}