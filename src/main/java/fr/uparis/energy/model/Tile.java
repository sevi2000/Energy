package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        NORTH_WEST;

        static Direction getFromIndex(Geometry geometry, int index) {
            Direction res = null;
            switch (geometry) {
                case HEXAGON -> res = switch (index) {
                    case 0 -> NORTH;
                    case 1 -> NORTH_EAST;
                    case 2 -> SOUTH_EAST;
                    case 3 -> SOUTH;
                    case 4 -> SOUTH_WEST;
                    case 5 -> NORTH_WEST;
                    default -> throw new IllegalStateException("Unexpected value: " + index);};

                case SQUARE -> res = switch (index) {
                    case 0 -> NORTH;
                    case 1 -> EAST;
                    case 2 -> SOUTH;
                    case 3 -> WEST;
                    default -> throw new IllegalStateException("Unexpected value: " + index);};
            }
            return res;
        }

        int getIndex(Geometry geom) {
            int res = -1;
            switch (geom) {
                case SQUARE -> res = switch (this) {
                    case NORTH -> 0;
                    case EAST -> 1;
                    case SOUTH -> 2;
                    case WEST -> 3;
                    default -> -1;};

                case HEXAGON -> res = switch (this) {
                    case NORTH -> 0;
                    case NORTH_EAST -> 1;
                    case SOUTH_EAST -> 2;
                    case SOUTH -> 3;
                    case SOUTH_WEST -> 4;
                    case NORTH_WEST -> 5;
                    default -> -1;};
            }
            return res;
        }

        public Direction getOpposite() {
            Direction res =
                    switch (this) {
                        case NORTH -> SOUTH;
                        case NORTH_EAST -> SOUTH_WEST;
                        case EAST -> WEST;
                        case SOUTH_EAST -> NORTH_WEST;
                        case SOUTH -> NORTH;
                        case SOUTH_WEST -> NORTH_EAST;
                        case WEST -> EAST;
                        case NORTH_WEST -> SOUTH_EAST;
                    };
            return res;
        }
    }

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

    private String connectorsToString() {
        String res = "";
        for (int i = 0; i < connectors.size(); i++) {
            if (connectors.get(i).exists()) res += i + " ";
        }
        return res.trim();
    }

    @Override
    public String toString() {
        String existingConnectorsList = this.connectorsToString();
        return String.format(
                existingConnectorsList.length() == 0 ? "%s%s" : "%s %s",
                this.component.toString(),
                existingConnectorsList);
    }

    public Map<Connector, Direction> getExistingConnectors() {
        HashMap<Connector, Direction> existingConnectors = new HashMap<>();
        for (Connector c : this.connectors) {
            if (c.exists()) {
                existingConnectors.put(c, Direction.getFromIndex(this.geometry, connectors.indexOf(c)));
            }
        }
        return existingConnectors;
    }

    /**
     * Gives the right index of the connector depending on geometry
     * @param dir the requested connector direction
     * @return the corresponding connector
     */
    public Connector getConnector(Direction dir) {
        return this.connectors.get(dir.getIndex(this.geometry));
    }
}
