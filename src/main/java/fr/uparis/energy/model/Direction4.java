package fr.uparis.energy.model;

public enum Direction4 implements Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    @Override
    public Direction getOppositeDirection() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }

    @Override
    public int getHeightOffset(int columnIndex) {
        return switch (this) {
            case NORTH -> -1;
            case SOUTH -> 1;
            case WEST, EAST -> 0;
        };
    }

    @Override
    public int getWidthOffset() {
        return switch (this) {
            case NORTH, SOUTH -> 0;
            case WEST -> -1;
            case EAST -> 1;
        };
    }
}
