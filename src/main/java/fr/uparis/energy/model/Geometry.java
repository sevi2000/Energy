package fr.uparis.energy.model;

/**
 * Represents the shape of a level tiles.
 */
public enum Geometry {
    SQUARE,
    HEXAGON;

    private static int[] card = {4, 6};

    /**
     * Casts a string representation to a Geometry.
     * @param s the given string.
     * @return the corresponding value.
     */
    public static Geometry fromString(String s) {
        return switch (s) {
            case "H" -> Geometry.HEXAGON;
            case "S" -> Geometry.SQUARE;
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Textual representation.
     * @return a string that can be written in a level file.
     */
    @Override
    public String toString() {
        return super.toString().substring(0, 1);
    }

    /**
     * Gives the number of sides of the given geometry.
     * @return an integer representing the number of sides.
     */
    public int card() {
        return card[this.ordinal()];
    }

    /**
     * Gives the Direction values corresponding to a given geometry.
     * @return an array with the Direction values for the given geometry.
     */
    public Direction[] getDirections() {
        return switch (this) {
            case SQUARE -> Direction4.values();
            case HEXAGON -> Direction6.values();
        };
    }

    public Geometry opposite() {
        return this == Geometry.SQUARE ? Geometry.HEXAGON : Geometry.SQUARE;
    }
    
}
