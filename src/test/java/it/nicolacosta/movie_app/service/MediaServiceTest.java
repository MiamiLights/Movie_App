package it.nicolacosta.movie_app.service;

import it.nicolacosta.movie_app.events.EventDispatcher;
import it.nicolacosta.movie_app.model.Media;
import it.nicolacosta.movie_app.persistence.MediaDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {
    // Mock crea un'istanza fantoccio della classe con gli stessi metodi della classe ma senza codice reale
    @Mock
    private MediaDAO mediaDAOMock;

    @Mock
    private EventDispatcher eventDispatcherMock;

    // Mockito crea un'istanza reale di MediaService ma passando gli oggetti fantoccio
    @InjectMocks
    private MediaService mediaService;

    @Test
    public void testAddMediaNotifyObservers() throws SQLException {

        InOrder inOrder = inOrder(mediaDAOMock, eventDispatcherMock);
        // Creiamo un oggetto Media finto
        Media m = mock(Media.class);

        // esegue il codice reale di mediaService credendo di parlare con il DAO reale, sta in realtà comunicando con il mock
        mediaService.addMedia(m);

        /*
        I mock "registrano"le chiamate su di loro effettuate, quando invochiamo verify andiamo a controllare se una certa chiamata di funzione é avvenuta o meno.
        É un controllo comportamentale. Nel caso specifico vogliamo verificare se il DAO venga interpellato o meno per salvare (addMedia).
        Successivamente vogliamo verificare che l'evento di notifica venga lanciato. Verifichiamo inoltre che la chiamata ai metodi avvenga secondo ordine corretto
        e che non avvengano ulteriori chiamate non previste successivamente.
         */

        inOrder.verify(mediaDAOMock).addMedia(m);
        inOrder.verify(eventDispatcherMock).notifyObservers();
        verifyNoMoreInteractions(mediaDAOMock, eventDispatcherMock);

    }

    @Test
    public void testDeleteMediaNotifiesObserver() throws SQLException{
        int mediaId = 1;
        InOrder inOrder = inOrder(mediaDAOMock, eventDispatcherMock);
        mediaService.deleteMedia(mediaId);

        inOrder.verify(mediaDAOMock).deleteMedia(mediaId);
        inOrder.verify(eventDispatcherMock).notifyObservers();
    }


}