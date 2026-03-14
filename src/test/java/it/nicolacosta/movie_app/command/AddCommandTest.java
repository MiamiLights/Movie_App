package it.nicolacosta.movie_app.command;

import it.nicolacosta.movie_app.builder.MovieBuilder;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.service.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import static org.mockito.Mockito.*;

class AddCommandTest {

    private MediaService serviceMock;
    private Media mediaTest;
    private AddCommand command;

    @BeforeEach
    void setUp() {
        serviceMock = mock(MediaService.class);
        mediaTest = new MovieBuilder().title("Test").build();
        command = new AddCommand(mediaTest, serviceMock);
    }

    @Test
    void execute_ShouldInvokeAddMedia() throws SQLException {
        command.execute();

        verify(serviceMock, times(1)).addMedia(mediaTest);
    }

    @Test
    void undo_ShouldInvokeDeleteMediaWithCorrectId() throws SQLException {
        command.undo();

        verify(serviceMock, times(1)).deleteMedia(mediaTest.getId());
    }

}