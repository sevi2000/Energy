package fr.uparis.energy.model;

import fr.uparis.energy.view.PowerState;

public interface ReadOnlyTile {
    Component getComponent();

    boolean[] getConnectorsExist();

    PowerState getState();
}
