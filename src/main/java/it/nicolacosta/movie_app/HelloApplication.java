package it.nicolacosta.movie_app;

import it.nicolacosta.movie_app.controller.MainPageController;
import it.nicolacosta.movie_app.events.EventDispatcher;
import it.nicolacosta.movie_app.persistence.DatabaseConnectionManager;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import it.nicolacosta.movie_app.service.MediaService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException, SQLException {

    MediaDAO dao = new MediaDAO(new DatabaseConnectionManager());
    EventDispatcher dispatcher = new EventDispatcher();
    MediaService service = new MediaService(dao, dispatcher);
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    fxmlLoader.setControllerFactory(type -> {
      if (type == MainPageController.class) {
          try {
              return new MainPageController(service, dispatcher);
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
      }
      return null;
    });
    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
    MainPageController controller = fxmlLoader.getController();

    stage.setTitle("Movie App");
    stage.setScene(scene);

    //TODO controllare meglio questa parte
    stage.setOnCloseRequest(t -> {
      EventDispatcher.getInstance().removeObserver(controller);
      Platform.exit();
      System.exit(0);
    });

    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) throws SQLException {
    launch();
  }
}
