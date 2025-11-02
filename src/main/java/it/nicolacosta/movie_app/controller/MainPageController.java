package it.nicolacosta.movie_app.controller;

import it.nicolacosta.movie_app.command.GetAllCommand;
import it.nicolacosta.movie_app.events.Observer;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable, Observer {

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private GridPane gridPane;
  private final MediaDAO mediaDAO;

  public MainPageController() throws SQLException {
    this.mediaDAO = new MediaDAO();
    this.mediaDAO.addObserver(this);
  }



  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    aggiornaGriglia();
  }

  private void aggiornaGriglia(){
    gridPane.getChildren().clear();
    gridPane.getRowConstraints().clear();
    gridPane.getColumnConstraints().clear();
    gridPane.setGridLinesVisible(true);
    // 2. Ricarica i dati aggiornati dal DAO
    GetAllCommand getAllCommand = new GetAllCommand(mediaDAO);
    List<Media> allMedia = new ArrayList<>();
    try {
       allMedia = getAllCommand.getAllMedia(); // Assumiamo esista mediaDAO.getAllMedia()
    }catch (SQLException e){
      e.printStackTrace();
    }

    // Se non ci sono media, potremmo mostrare un messaggio
    if (allMedia == null || allMedia.isEmpty()) {
      Label emptyLabel = new Label("Nessun media trovato nel database.");
      gridPane.add(emptyLabel, 0, 0);
      return;
    }

    // 3. Imposta le colonne come nel tuo esempio
    int numColumns = 5; // Puoi cambiare questo valore a tuo piacimento (es. 10)
    for (int i = 0; i < numColumns; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setPercentWidth(100.0 / numColumns); // Colonne di uguale larghezza
      gridPane.getColumnConstraints().add(colConst);
    }

    // 4. Popola la griglia dinamicamente
    int col = 0;
    int row = 0;

    for (Media media : allMedia) {
      // Se siamo all'inizio di una nuova riga (colonna 0),
      // dobbiamo prima creare la RowConstraint per quella riga.
      if (col == 0) {
        RowConstraints rowConst = new RowConstraints();
        rowConst.setMinHeight(120); // La tua altezza minima
        rowConst.setVgrow(Priority.SOMETIMES); // La tua impostazione di crescita
        gridPane.getRowConstraints().add(rowConst);
      }

      // Crea il contenuto da visualizzare (es. un VBox con il titolo)
      // Questo rende più facile aggiungere un'immagine in futuro
      VBox mediaBox = new VBox();
      mediaBox.setStyle("-fx-alignment: center;"); // Centra il contenuto
      Label titleLabel = new Label(media.getTitle()); // Assumiamo media.getTitolo()
      mediaBox.getChildren().add(titleLabel);

      // Aggiungi il nodo alla cella corretta
      gridPane.add(mediaBox, col, row);

      // Aggiorna le coordinate per il prossimo elemento
      col++;
      if (col == numColumns) {
        // Vai a capo: resetta la colonna e incrementa la riga
        col = 0;
        row++;
      }
    }
  }

  @Override
  public void onDatabaseChanged() {
    Platform.runLater(this::aggiornaGriglia);
  }
}
