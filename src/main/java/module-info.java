module it.nicolacosta.movie_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens it.nicolacosta.movie_app to javafx.fxml;
    exports it.nicolacosta.movie_app;
    exports it.nicolacosta.movie_app.model;
    opens it.nicolacosta.movie_app.model to javafx.fxml;
    exports it.nicolacosta.movie_app.controller;
    opens it.nicolacosta.movie_app.controller to javafx.fxml;
}