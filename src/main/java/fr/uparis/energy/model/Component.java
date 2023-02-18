package fr.uparis.energy.model;

public class Component {

    protected Tile parentTile;

    protected Component() {

    }

    public boolean isPowered() {
        return false;
    }
}
