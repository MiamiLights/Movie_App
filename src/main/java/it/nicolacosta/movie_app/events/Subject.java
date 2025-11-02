package it.nicolacosta.movie_app.events;

public interface Subject {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);

    void notifyObservers();
}
