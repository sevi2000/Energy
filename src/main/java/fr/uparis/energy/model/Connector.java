package fr.uparis.energy.model;

public class Connector {
    private boolean exists;
    // private boolean isPowered;
    private final Tile parentTile;

    private Direction direction;

    private Connector neighbor;

    public Connector(Tile parentTile, boolean exists, Direction direction) {
        this.parentTile = parentTile;
        this.exists = exists;
        this.direction = direction;
    }

    public boolean exists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    // public boolean isPowered() {
    // return isPowered;
    // }

    // public void setPowered(boolean powered) {
    // isPowered = powered;
    // }

    /*public void setParentTile(Tile t) {
        this.parentTile = t;
    }*/

    public void setNeighbor(Connector c) {
        this.neighbor = c;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
