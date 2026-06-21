package it.nicolacosta.movie_app.controller;

import it.nicolacosta.movie_app.builder.*;
import it.nicolacosta.movie_app.command.*;
import it.nicolacosta.movie_app.events.EventDispatcher;
import it.nicolacosta.movie_app.events.Observer;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.TvSeries;
import it.nicolacosta.movie_app.service.MediaService;
import it.nicolacosta.movie_app.strategy.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable, Observer {

    @FXML private TableView<Media> mediaTable;
    @FXML private ComboBox<String> genreFilterComboBox, genreComboBox, typeComboBox, statusComboBox;
    @FXML private TextField yearFilterField, titleField, directorField, yearField, episodesField, seasonsField, titleFilterField;
    @FXML private Slider ratingSlider;
    @FXML private VBox tvSeriesFields;
    @FXML private TableColumn<Media, Integer> seasonsCol;
    @FXML private TableColumn<Media, Integer> episodesCol;

    private final MediaService mediaService;
    private FilterStrategy currentStrategy = new NoFilterStrategy();
    private final CommandManager commandManager = new CommandManager();
    private Media selectedMedia;

    public MainPageController(MediaService mediaService, EventDispatcher eventDispatcher){
        this.mediaService = mediaService;
        eventDispatcher.addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        refreshTable();
    }

    private void refreshTable() {
        List<Media> allMedia = mediaService.getAllMedia();
        if (allMedia != null) {
            mediaTable.setItems(FXCollections.observableArrayList(currentStrategy.filter(allMedia)));
        }
    }

    // Quando i campi dei filtri vengono modificati viene triggerato questo metodo
    @FXML
    public void onFilterChanged() {

        CompositeFilterStrategy composite = new CompositeFilterStrategy();

        String genre = genreFilterComboBox.getValue();
        String yearStr = yearFilterField.getText();
        String title = titleFilterField.getText();

        if (genre != null && !genre.isBlank() && !"Tutti".equalsIgnoreCase(genre))
            composite.addStrategy(new FilterByGenreStrategy(genre));

        if (yearStr != null && !yearStr.isBlank()) {
            try {
                composite.addStrategy(new FilterByYearStrategy(Integer.parseInt(yearStr)));
            } catch (NumberFormatException ignored) {}
        }
        if (title != null && !title.isBlank())
            composite.addStrategy(new FilterByNameStrategy(title));
        this.currentStrategy = composite;
        List<Media> filtered = mediaService.filterData(composite);
        mediaTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    public void onResetFilters() {
        genreFilterComboBox.setValue("Tutti");
        yearFilterField.clear();
        titleFilterField.clear();
        currentStrategy = new NoFilterStrategy();
        refreshTable();
    }

    @FXML
    public void onSaveMedia() {
        try {
            MediaDTO dto = extractDTOFromUI();
            MediaValidator.validate(dto);

            MediaDirector director = new MediaDirector();
            Media media = director.buildMedia(dto);

            if (selectedMedia != null)
                media.setId(selectedMedia.getId());

            // creiamo il comando
            Command command = (selectedMedia != null)
                    ? new EditCommand(media, mediaService)
                    : new AddCommand(media, mediaService);

            safeExecuteCommand(command);

        } catch (IllegalArgumentException e){
            AlertUtils.showError("Errore durante il salvataggio. Controllare i dati inseriti.");
        } catch (Exception e){
            AlertUtils.showError("Errore imprevisto: " + e.getMessage());
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
                AlertUtils.showError("Errore durante l'eliminazione: " + e.getMessage());
            }
        } else {
            AlertUtils.showError("Seleziona un elemento dalla tabella.");
        }
    }

    @FXML
    public void onTypeSelected() {
        boolean isTv = "Serie TV".equals(typeComboBox.getValue());
        tvSeriesFields.setVisible(isTv);
        tvSeriesFields.setManaged(isTv);
    }

    public void onEditMedia(){
        selectedMedia = mediaTable.getSelectionModel().getSelectedItem();
        if (selectedMedia != null){
            fillForm();
        } else {
            AlertUtils.showError("Seleziona un elemento dalla tabella.");
        }
    }

    private void clearFields() {
        titleField.clear();
        directorField.clear();
        yearField.clear();
        episodesField.clear();
        seasonsField.clear();
        selectedMedia = null;
    }


    @Override
    public void onDatabaseChanged() {
        Platform.runLater(this::refreshTable);
    }

    public void onUndo(){
        try {
            commandManager.undo();
        }catch (SQLException e){
            AlertUtils.showError("Errore durante l'annullamento: " + e.getMessage());
        }
    }

    private void safeExecuteCommand(Command command){
        try {
            commandManager.executeCommand(command);
            clearFields();
        }catch (SQLException e){
            AlertUtils.showError("Errore database " + e.getMessage());
        }
    }

    private void fillForm(){
        titleField.setText(selectedMedia.getTitle());
        directorField.setText(selectedMedia.getDirector());
        yearField.setText(String.valueOf(selectedMedia.getYear()));
        ratingSlider.setValue(selectedMedia.getRating());
        genreComboBox.setValue(selectedMedia.getGenre());
        statusComboBox.setValue(selectedMedia.getStatus());
        typeComboBox.setValue(selectedMedia.getType());

        if (selectedMedia instanceof TvSeries){
            episodesField.setText(String.valueOf(((TvSeries) selectedMedia).getEpCount()));
            seasonsField.setText(String.valueOf(((TvSeries) selectedMedia).getSeasonCount()));
            onTypeSelected();
        } else {
            typeComboBox.setValue("Film");
            onTypeSelected();
        }
    }

    private Integer parseSafeInt(String text) {
        try { return Integer.parseInt(text); }
        catch (Exception e) { return 0; }
    }


    private MediaDTO extractDTOFromUI() {
        return new MediaDTO().withDirector(directorField.getText())
                .withGenre(genreComboBox.getValue())
                .withRating((int) ratingSlider.getValue())
                .withStatus(statusComboBox.getValue())
                .withTitle(titleField.getText())
                .withYear(parseSafeInt(yearField.getText()))
                .withSeasons(parseSafeInt(seasonsField.getText()))
                .withEpisodes(parseSafeInt(episodesField.getText()))
                .withType(typeComboBox.getValue());
    }


    private void setupColumns(){
        seasonsCol.setCellValueFactory(data -> {
            if (data.getValue() instanceof TvSeries) {
                return new javafx.beans.property.SimpleIntegerProperty(((TvSeries) data.getValue()).getSeasonCount()).asObject();
            } else {
                return null;
            }
        });

        episodesCol.setCellValueFactory(data -> {
            if (data.getValue() instanceof TvSeries) {
                return new javafx.beans.property.SimpleIntegerProperty(((TvSeries) data.getValue()).getEpCount()).asObject();
            } else {
                return null;
            }
        });
    }
}
