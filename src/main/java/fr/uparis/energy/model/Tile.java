package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tile that is held by a board.
 */
public class Tile {
    private final List<Connector> connectors = new ArrayList<>();
    private Component component;
    private final Geometry geometry;

    /**
     * Builds a Tile with the given specification.
     * @param geometry of this tile.
     * @param connectedEdges of this tile.
     * @param component of this tile.
     */
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

    /**
     * Builds a Tile with the given geometry, an empty component and no connectors.
     * @param geometry of the resulting tile.
     */
    public Tile(Geometry geometry) {
        this.geometry = geometry;

        this.component = new EmptyComponent();

        for (int i = 0; i < geometry.card(); i++) {
            connectors.add(new Connector(this, false, geometry.getDirections()[i]));
        }
    }

    /**
     * Rotates given tile by 90 or 60 degrees clockwise depending on its geometry.
     * @param propagateEnergy tells if we need to try propagating energy.
     */
    public void rotateClockwise(boolean propagateEnergy) {}

    /**
     * Rotates given tile by 90 or 60 degrees counter clockwise depending on its geometry.
     * @param propagateEnergy tells if we need to try propagating energy.
     */
    public void rotateCounterClockwise(boolean propagateEnergy) {}

    /**
     * Allows to change this tile component.
     */
    public void cycleComponent() {}

    /**
     * Textual representation of this tile connector.
     * @return a String that can be written in a level file.
     */
    private String connectorsToString() {
        String res = "";
        for (int i = 0; i < connectors.size(); i++) {
            if (connectors.get(i).exists()) res += i + " ";
        }
        return res.trim();
    }

    /**
     * Textual representation of this tile.
     * @return a String that can be written in a level file.
     */
    @Override
    public String toString() {
        String existingConnectorsList = this.connectorsToString();
        return String.format(
                existingConnectorsList.length() == 0 ? "%s%s" : "%s %s",
                this.component.toString(),
                existingConnectorsList);
    }

    /**
     * Gives the right index of the connector depending on geometry
     *
     * @param direction the requested connector direction
     * @return the corresponding connector
     */
    public Connector getConnector(Direction direction) {
        for (Connector c : connectors) if (c.getDirection() == direction) return c;
        throw new IllegalArgumentException();
    }

    /**
     * Gives this tile connectors.
     * @return a list containing this tile connectors.
     */
    public List<Connector> getConnectors() {
        return this.connectors;
    }
}
