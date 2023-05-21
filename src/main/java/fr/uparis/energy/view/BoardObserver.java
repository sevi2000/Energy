package fr.uparis.energy.view;

import fr.uparis.energy.model.ReadOnlyBoard;

/**
 * Board observer interface necessary for Observer pattern.
 */
public interface BoardObserver {

    /**
     * Updates the view.
     * @param rob model from which to get information.
     */
    void update(ReadOnlyBoard rob);
}
