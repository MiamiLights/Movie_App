package it.nicolacosta.movie_app.persistence;

import it.nicolacosta.movie_app.builder.MovieBuilder;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class MediaMapperTest {

    private MediaMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new MediaMapper();
    }

    @Test
    void testResultSetToMovie() throws SQLException{

        // Creiamo un finto ResultSet
        ResultSet rs = mock(ResultSet.class);

        // TODO definire meglio cosa fa questa parte
        // Definiamo cosa deve rispondere quando il Mapper chiamerà i suoi metodi.
        when(rs.getString("type")).thenReturn("Film");
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("title")).thenReturn("Inception");
        when(rs.getString("director")).thenReturn("Nolan");


        Media result = mapper.resultSetToMedia(rs);

        assertInstanceOf(Movie.class, result);
        assertEquals("Inception", result.getTitle());
        assertEquals(0, result.getYear());

    }

    @Test
    void shouldWriteOnStatement() throws SQLException {
        // Creiamo un finto PreparedStatement per verificare se il mapper ci scrive sopra
        PreparedStatement stmt = mock(PreparedStatement.class);

        // Creiamo un oggetto Media REALE con dei dati di prova
        Media movie = new MovieBuilder()
                .title("Matrix")
                .director("Wachowski").build();

        MediaMapper mapper = new MediaMapper();
        int nextIndex = mapper.completeStatement(movie, stmt);

        // Verifichiamo se il Mapper ha chiamato i metodi del DB.
        verify(stmt).setString(1, "Matrix");
        verify(stmt).setString(2, "Wachowski");
        // Verifichiamo che il metodo ci restituisca l'indice corretto per il parametro successivo
        assertEquals(10, nextIndex);
        }

}