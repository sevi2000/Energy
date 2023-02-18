package fr.uparis.energy.model;

import java.util.List;

public class Tile {
    private List<Connector> connectors;
    private Component component;
    private Geometry geometry;
    private Board parentBoard;
    private int orientation;

    enum Direction {NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST};
    public void rotateClockwise() {

    }

    public void rotateCounterClockwise() {

    }

    public void calculatePower() {

    }

    public Tile(Geometry geometry) {

    }

    public void cycleComponent() {

    }

    public boolean toggleConnectorExists(Direction direction) {
        return false;
    }

}
