package fr.uparis.energy.model;

public interface Direction {
    /*static Direction getFromIndex(Geometry geometry, int index) {
        Direction res = null;
        switch (geometry) {
            case HEXAGON -> res = switch (index) {
                case 0 -> NORTH;
                case 1 -> NORTH_EAST;
                case 2 -> SOUTH_EAST;
                case 3 -> SOUTH;
                case 4 -> SOUTH_WEST;
                case 5 -> NORTH_WEST;
                default -> throw new IllegalStateException("Unexpected value: " + index);};

            case SQUARE -> res = switch (index) {
                case 0 -> NORTH;
                case 1 -> EAST;
                case 2 -> SOUTH;
                case 3 -> WEST;
                default -> throw new IllegalStateException("Unexpected value: " + index);};
        }
        return res;
    }

    int getIndex(Geometry geom) {
        int res = -1;
        switch (geom) {
            case SQUARE -> res = switch (this) {
                case NORTH -> 0;
                case EAST -> 1;
                case SOUTH -> 2;
                case WEST -> 3;
                default -> -1;};

            case HEXAGON -> res = switch (this) {
                case NORTH -> 0;
                case NORTH_EAST -> 1;
                case SOUTH_EAST -> 2;
                case SOUTH -> 3;
                case SOUTH_WEST -> 4;
                case NORTH_WEST -> 5;
                default -> -1;};
        }
        return res;
    }*/

    public Direction getOppositeDirection();

    public int getHeightOffset(int columnIndex);

    public int getWidthOffset();
}
