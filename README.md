# Movie App

Applicazione desktop per la gestione di film e serie TV, sviluppata in Java utilizzando JavaFX e Maven. Il progetto implementa diversi design pattern per garantire una struttura solida, estensibile e manutenibile.

## Caratteristiche principali

- Gestione catalogo: Aggiunta, modifica ed eliminazione di film e serie TV.
- Filtraggio avanzato: Ricerca dei contenuti per nome, genere o anno di uscita.
- Persistenza dati: Integrazione con database SQL per il salvataggio delle informazioni.
- Architettura robusta: Utilizzo di pattern architetturali per separare la logica di business dall'interfaccia grafica.

## Tecnologie utilizzate

- Java 17+
- JavaFX (Interfaccia grafica)
- Maven (Gestione delle dipendenze e build)
- SQL (Persistenza dei dati)
- Docker (Opzionale, per l'ambiente database)

## Design Pattern implementati

Il progetto mette in pratica diversi concetti di ingegneria del software:

- Builder: Per la creazione flessibile di oggetti Movie e TvSeries.
- Command: Per la gestione delle operazioni di modifica (Add, Edit, Delete) e potenziale supporto all'annullamento delle azioni.
- Strategy: Per l'implementazione di diversi criteri di filtraggio dei contenuti.
- Observer: Per la gestione degli eventi e l'aggiornamento reattivo dell'interfaccia.
- DAO (Data Access Object): Per l'astrazione dello strato di persistenza.

## Struttura del progetto

- src/main/java: Contiene il codice sorgente suddiviso per package (builder, command, controller, model, persistence, service, strategy).
- src/main/resources: Contiene i file di configurazione (.properties) e le viste JavaFX (.fxml).
- src/test/java: Contiene i test unitari per i componenti principali.

## Come avviare l'applicazione

1. Prerequisiti: Assicurati di avere installato JDK e Maven.
2. Database: Configura le credenziali nel file `src/main/resources/db.properties` o utilizza il `docker-compose.yml` se previsto.
3. Build: Esegui il comando `mvn clean install` nella root del progetto.
4. Esecuzione: Avvia l'applicazione tramite il comando `mvn javafx:run`.
