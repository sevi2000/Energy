package fr.uparis.energy.model;

import fr.uparis.energy.utils.IntPair;

public interface ReadOnlyBoard {
    ReadOnlyTile getTileAt(int i, int j);

    int getWidth();

    int getHeight();
    Geometry getGeometry();

    boolean isSolved();

    void rotateTileClockWise(IntPair clickedPolygon);
    void cycleTileComponent(IntPair clickedPolygon);
}
