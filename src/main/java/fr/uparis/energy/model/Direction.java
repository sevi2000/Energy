package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;

/** Represents a Direction to which a connector points. */
public interface Direction {

  /** {@return an array containing all directions} */
  Direction[] elements();

  /** {@return the ordinal of this direction} */
  int ord();

  /**
   * Returns the opposite direction of the caller.
   *
   * @return a Direction object representing the opposite side.
   */
  default Direction getOppositeDirection() {
    return this.elements()[(this.ord() + this.elements().length / 2) % this.elements().length];
  }

  /**
   * Gives the line number depending on Direction value and column index parity.
   *
   * @param columnIndex in which this direction is.
   * @return an integer representing the offset.
   */
  public int getHeightOffset(int columnIndex);

  /**
   * Gives the column Direction value.
   *
   * @return an integer representing the offset.
   */
  public int getWidthOffset();

  static List<Direction> getTrigonometricalOrderedDirection() {
    return new ArrayList<>();
  }
}
