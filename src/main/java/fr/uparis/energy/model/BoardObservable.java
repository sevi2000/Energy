package fr.uparis.energy.model;

import fr.uparis.energy.view.BoardObserver;

public interface BoardObservable {
    ReadOnlyTile getTileAt(int i, int j);

    int getWidth();

    int getHeight();

    void addObserver(BoardObserver o);

    void notifyObservers();
}
