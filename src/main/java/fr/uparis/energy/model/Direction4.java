package fr.uparis.energy.model;

public enum Direction4 implements Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    /**
     * Gives the opposite Direction of the caller.
     * @return Direction object representing the caller's opposite.
     */
    @Override
    public Direction getOppositeDirection() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
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
            case WEST, EAST -> 0;
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
            case WEST -> -1;
            case EAST -> 1;
        };
    }
}
