package it.nicolacosta.movie_app.events;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher implements Subject{

    private static EventDispatcher instance;
    private final List<Observer> observers = new ArrayList<>();

    public static EventDispatcher getInstance(){
        if (instance == null)
            instance = new EventDispatcher();
        return instance;
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers )
            observer.onDatabaseChanged();
    }
}
