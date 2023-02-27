package fr.uparis.energy.model;

import java.util.ArrayList;
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

    public Tile(Geometry geometry, boolean[] connectedEdges, String component) {
        this.geometry = geometry;
        switch (component) {
            case "L" -> this.component = new LampComponent();
            case "W" -> this.component = new WifiComponent();
            case "." -> this.component = new EmptyComponent();
            case "S" -> this.component = new SourceComponent();
        }

        connectors = new ArrayList<>();
        for (int i = 0; i < geometry.card(); i++) {
            connectors.add(new Connector(this, connectedEdges[i]));
        }
    }

    public void rotateClockwise(boolean propagateEnergy) {}

    public void rotateCounterClockwise(boolean propagateEnergy) {}

    public void calculatePower() {}

    public void cycleComponent() {}

    public boolean toggleConnectorExists(Direction direction) {
        return false;
    }
}
