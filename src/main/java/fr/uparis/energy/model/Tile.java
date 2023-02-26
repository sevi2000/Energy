package fr.uparis.energy.model;

import java.util.List;

public class Tile {
    private List<Connector> connectors;
    private Component component;
    private Geometry geometry;
    private Board parentBoard;
    private int orientation;

    enum Direction {
        NORTH,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    };

    public Tile(Geometry geometry, int[] connectedEdges, String component) {

    }

    public void rotateClockwise(boolean propagateEnergy) {}

    public void rotateCounterClockwise(boolean propagateEnergy) {}

    public void calculatePower() {}

    public void cycleComponent() {}

    public boolean toggleConnectorExists(Direction direction) {
        return false;
    }
}
