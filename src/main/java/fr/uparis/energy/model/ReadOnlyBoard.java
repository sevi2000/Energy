package fr.uparis.energy.model;

public interface ReadOnlyBoard {
  ReadOnlyTile getTileAt(int i, int j);

  int getWidth();

  int getHeight();

  Geometry getGeometry();

  boolean isSolved();
}
