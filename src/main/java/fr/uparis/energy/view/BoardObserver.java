package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;

public interface BoardObserver {
    void update(BoardObservable boardObservable);

}
