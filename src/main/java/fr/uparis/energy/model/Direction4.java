package fr.uparis.energy.model;

import java.util.List;

public enum Direction4 implements Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  @Override
  public Direction[] elements() {
    return Direction4.values();
  }

  @Override
  public int ord() {
    return this.ordinal();
  }

  /**
   * Gives the line number depending on Direction value and column index parity.
   *
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
   *
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

  public static List<Direction> getTrigonometricalOrderedDirection() {
    return List.of(NORTH, WEST, SOUTH, EAST);
  }
}
