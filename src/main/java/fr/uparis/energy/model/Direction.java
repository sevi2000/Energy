package fr.uparis.energy.model;

/**
 * Represents a Direction to which a connector points.
 */
public interface Direction {

    /**
     * Returns the opposite direction of the caller.
     * @return a Direction object representing the opposite side.
     */
    public Direction getOppositeDirection();

    /**
     * Gives the line number depending on Direction value and column index parity.
     * @param columnIndex in which this direction is.
     * @return an integer representing the offset.
     */
    public int getHeightOffset(int columnIndex);

    /**
     * Gives the column Direction value.
     * @return an integer representing the offset.
     */
    public int getWidthOffset();
}
