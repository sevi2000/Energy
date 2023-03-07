package fr.uparis.energy.model;

/**
 * Represents a connector held by a Tile.
 */
public class Connector {
    private boolean exists;
    private final Tile parentTile;

    private Direction direction;

    private Connector neighbor;

    /**
     * Builds a Connector with the given specification.
     * @param parentTile on which this connector is
     * @param exists if this connector represents a wire
     * @param direction side on which the connector is
     */
    public Connector(Tile parentTile, boolean exists, Direction direction) {
        this.parentTile = parentTile;
        this.exists = exists;
        this.direction = direction;
    }

    /**
     * Checks if this connector exists. It means if we can pass energy through it.
     * @return true if it exists.
     */
    public boolean exists() {
        return exists;
    }

    /**
     * Existance setter
     * @param exists value to set.
     */
    public void setExists(boolean exists) {
        this.exists = exists;
    }

    /**
     * Neighbor setter.
     * @param c to connect to.
     */
    public void setNeighbor(Connector c) {
        this.neighbor = c;
    }

    /**
     * Direction getter.
     * @return the direction of this connector according to its parentTile.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Neighbor getter.
     * @return the neighbor Connector of this Connector.
     */
    public Connector getNeighbor() {
        return this.neighbor;
    }

    public Tile getParentTile() {
        return this.parentTile;
    }

    public boolean hasPath() {
        return this.exists && this.neighbor.exists();
    }
}
