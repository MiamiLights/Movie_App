module it.nicolacosta.movie_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens it.nicolacosta.movie_app to javafx.fxml;
    exports it.nicolacosta.movie_app;
}