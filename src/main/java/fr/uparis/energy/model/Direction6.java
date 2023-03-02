package fr.uparis.energy.model;

public enum Direction6 implements Direction {
    NORTH,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    NORTH_WEST;

    @Override
    public Direction getOppositeDirection() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    /**
     * Gives the line number depending on Direction value and column index parity.
     * @param columnIndex in which this direction is.
     * @return an integer representing the offset.
     */
    @Override
    public int getHeightOffset(int columnIndex) {
        return switch (this) {
            case NORTH -> -1;
            case SOUTH -> 1;
            case NORTH_WEST, NORTH_EAST -> columnIndex % 2 == 0 ? -1 : 0;
            case SOUTH_WEST, SOUTH_EAST -> columnIndex % 2 == 0 ? 0 : 1;
        };
    }

    /**
     * Gives the column Direction value.
     * @return an integer representing the offset.
     */
    @Override
    public int getWidthOffset() {
        return switch (this) {
            case NORTH, SOUTH -> 0;
            case NORTH_WEST, SOUTH_WEST -> -1;
            case NORTH_EAST, SOUTH_EAST -> 1;
        };
    }
}
