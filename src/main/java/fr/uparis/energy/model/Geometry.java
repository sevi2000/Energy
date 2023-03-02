package fr.uparis.energy.model;

public enum Geometry {
    SQUARE,
    HEXAGON;

    private static int[] card = {4, 6};

    public static Geometry fromString(String s) {
        return switch (s) {
            case "H" -> Geometry.HEXAGON;
            case "S" -> Geometry.SQUARE;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public String toString() {
        return super.toString().substring(0, 1);
    }

    public int card() {
        return card[this.ordinal()];
    }

    public Direction[] getDirections() {
        return switch (this) {
            case SQUARE -> Direction4.values();
            case HEXAGON -> Direction6.values();
        };
    }
}
