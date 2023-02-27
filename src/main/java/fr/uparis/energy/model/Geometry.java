package fr.uparis.energy.model;

public enum Geometry {
    SQUARE,
    HEXAGON;

    private static int[] card = {4, 6};

    public static Geometry fromString(String s) {
        switch (s) {
            case "H" -> {
                return Geometry.HEXAGON;
            }
            case "S" -> {
                return Geometry.SQUARE;
            }
        }
        return Geometry.HEXAGON;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, 1);
    }

    public int card() {
        return card[this.ordinal()];
    }
}
