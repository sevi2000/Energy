package fr.uparis.energy.model;

import fr.uparis.energy.view.BoardObserver;

public interface BoardObservable {
    void addObserver(BoardObserver o);

    void notifyObservers();
}
