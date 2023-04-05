package fr.uparis.energy.model;

public interface ReadOnlyTile {
    Component getComponent();

    boolean[] getConnectorsExist();
}
