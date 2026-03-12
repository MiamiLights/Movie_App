package it.nicolacosta.movie_app.controller;

import it.nicolacosta.movie_app.command.AddCommand;
import it.nicolacosta.movie_app.command.Command;
import it.nicolacosta.movie_app.command.CommandManager;
import it.nicolacosta.movie_app.command.DeleteCommand;
import it.nicolacosta.movie_app.events.EventDispatcher;
import it.nicolacosta.movie_app.events.Observer;
import it.nicolacosta.movie_app.factory.MovieFactory;
import it.nicolacosta.movie_app.factory.TvSeriesFactory;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import it.nicolacosta.movie_app.service.MediaService;
import it.nicolacosta.movie_app.strategy.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainPageController implements Initializable, Observer {

    @FXML private TableView<Media> mediaTable;
    @FXML private ComboBox<String> genreFilterComboBox, genreComboBox, typeComboBox, statusComboBox;
    @FXML private TextField yearFilterField, titleField, directorField, yearField, episodesField, seasonsField;
    @FXML private Slider ratingSlider;
    @FXML private VBox tvSeriesFields;

    private final MediaService mediaService;
    private FilterStrategy currentStrategy = new NoFilterStrategy();
    private final CommandManager commandManager = new CommandManager();

    public MainPageController() throws SQLException {
        MediaDAO mediaDAO = MediaDAO.getInstance();
        this.mediaService = new MediaService(mediaDAO, EventDispatcher.getInstance());
        EventDispatcher.getInstance().addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
    }

    private void refreshTable() {
        List<Media> allMedia = mediaService.getAllMedia();
        if (allMedia != null) {
            mediaTable.setItems(FXCollections.observableArrayList(currentStrategy.filter(allMedia)));
        }
    }

    @FXML
    public void onFilterChanged() {
        String genre = genreFilterComboBox.getValue();
        String yearStr = yearFilterField.getText();

        if (genre != null && !"Tutti".equals(genre)) {
            currentStrategy = new FilterByGenreStrategy(genre);
        } else if (yearStr != null && !yearStr.isEmpty()) {
            try {
                currentStrategy = new FilterByYearStrategy(Integer.parseInt(yearStr));
            } catch (NumberFormatException e) {
                currentStrategy = new NoFilterStrategy();
            }
        } else {
            currentStrategy = new NoFilterStrategy();
        }
        refreshTable();
    }

    @FXML
    public void onResetFilters() {
        genreFilterComboBox.setValue("Tutti");
        yearFilterField.clear();
        currentStrategy = new NoFilterStrategy();
        refreshTable();
    }

    @FXML
    public void onSaveMedia() {
        try {
            String type = typeComboBox.getValue();
            int y = Integer.parseInt(yearField.getText());
            int r = (int) ratingSlider.getValue();
            Media media;
            Map<String, Object> data = new HashMap<>();
            data.put("title", titleField.getText());
            data.put("director", directorField.getText());
            data.put("genre", genreComboBox.getValue());
            data.put("status", statusComboBox.getValue());
            data.put("year", y);
            data.put("rating", r);
            data.put("type", typeComboBox.getValue());

            if ("Film".equals(type)) {
                MovieFactory factory = new MovieFactory();
                media = factory.createFromData(data);
            } else {
                TvSeriesFactory factory = new TvSeriesFactory();
                data.put("episodes", Integer.parseInt(episodesField.getText()));
                data.put("seasons", Integer.parseInt(seasonsField.getText()));
                media = factory.createFromData(data);
            }
            Command addCmd = new AddCommand(media, mediaService);
            commandManager.executeCommand(addCmd);
            clearFields();

        } catch (Exception e) {
            showError("Dati non validi: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteMedia() {
        Media selected = mediaTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Command deleteCmd = new DeleteCommand(selected, mediaService);
                commandManager.executeCommand(deleteCmd);
            }catch (SQLException e){
                showError("Errore durante l'eliminazione: " + e.getMessage());
            }
        } else {
            showError("Seleziona un elemento dalla tabella!");
        }
    }

    @FXML
    public void onTypeSelected() {
        boolean isTv = "Serie TV".equals(typeComboBox.getValue());
        tvSeriesFields.setVisible(isTv);
        tvSeriesFields.setManaged(isTv);
    }

    private void clearFields() {
        titleField.clear(); directorField.clear(); yearField.clear(); episodesField.clear(); seasonsField.clear();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR); a.setContentText(msg); a.show();
    }

    @Override
    public void onDatabaseChanged() {
        Platform.runLater(this::refreshTable);
    }

    public void onUndo(){
        try {
            commandManager.undo();
        }catch (SQLException e){
            showError("Errore durante l'annullamento: " + e.getMessage());
        }
    }
}
