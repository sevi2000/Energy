package fr.uparis.energy.model;

public enum Geometry {
    SQUARE,
    HEXAGON;

    private static int[] card = {4, 6};

    public int card() {
        return card[this.ordinal()];
    }
}
