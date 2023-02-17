package fr.uparis.energy.model.energy.model.energy.model;

public class Connector {
    private boolean exists;

    public boolean exists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isPowered() {
        return isPowered;
    }

    public void setPowered(boolean powered) {
        isPowered = powered;
    }

    private boolean isPowered;
}
