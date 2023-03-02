package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final List<Connector> connectors = new ArrayList<>();
    private Component component;
    private final Geometry geometry;
    // private Board parentBoard;
    // private int orientation;

    public Tile(Geometry geometry, boolean[] connectedEdges, String component) {
        this.geometry = geometry;

        this.component = switch (component) {
            case "L" -> new LampComponent();
            case "W" -> new WifiComponent();
            case "." -> new EmptyComponent();
            case "S" -> new SourceComponent();
            default -> throw new IllegalArgumentException();};

        // connectors = new ArrayList<>();
        for (int i = 0; i < geometry.card(); i++) {
            connectors.add(new Connector(this, connectedEdges[i], geometry.getDirections()[i]));
        }
    }

    public Tile(Geometry geometry) {
        this.geometry = geometry;

        this.component = new EmptyComponent();

        // connectors = new ArrayList<>();
        for (int i = 0; i < geometry.card(); i++) {
            connectors.add(new Connector(this, false, geometry.getDirections()[i]));
        }
    }

    public void rotateClockwise(boolean propagateEnergy) {}

    public void rotateCounterClockwise(boolean propagateEnergy) {}

    // public void calculatePower() {}

    public void cycleComponent() {}

    /*public boolean toggleConnectorExists(Direction direction) {
        return false;
    }*/

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

    /*public Map<Connector, Direction> getExistingConnectors() {
        HashMap<Connector, Direction> existingConnectors = new HashMap<>();
        for (Connector c : this.connectors) {
            if (c.exists()) {
                existingConnectors.put(c, Direction.getFromIndex(this.geometry, connectors.indexOf(c)));
            }
        }
        return existingConnectors;
    }*/

    /**
     * Gives the right index of the connector depending on geometry
     *
     * @param dir the requested connector direction
     * @return the corresponding connector
     */
    public Connector getConnector(Direction direction) {
        for (Connector c : connectors) if (c.getDirection() == direction) return c;
        throw new IllegalArgumentException();
        // return this.connectors.get(direction.getIndex(this.geometry));
    }

    public List<Connector> getConnectors() {
        return this.connectors;
    }
}
