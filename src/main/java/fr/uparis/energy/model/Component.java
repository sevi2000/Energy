package fr.uparis.energy.model;

public abstract class Component {

    protected Tile parentTile;

    protected Component() {}

    public boolean isPowered() {
        return false;
    }

    public abstract String toString();
}
