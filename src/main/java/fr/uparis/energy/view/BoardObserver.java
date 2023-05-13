package fr.uparis.energy.view;

import fr.uparis.energy.model.ReadOnlyBoard;

public interface BoardObserver {
    void update(ReadOnlyBoard rob);

}
