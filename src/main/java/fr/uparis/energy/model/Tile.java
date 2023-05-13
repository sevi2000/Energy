package fr.uparis.energy.model;

import fr.uparis.energy.view.PowerState;
import java.util.ArrayList;
import java.util.List;

/** Represents a tile that is held by a board. */
public class Tile implements ReadOnlyTile {
  private List<Connector> connectors = new ArrayList<>();
  private Component component;
  private Geometry geometry;

  private boolean isPowered = false;

  /**
   * Builds a Tile with the given specification.
   *
   * @param geometry of this tile.
   * @param connectedEdges of this tile.
   * @param component of this tile.
   */
  public Tile(Geometry geometry, boolean[] connectedEdges, Component component) {
    if (connectedEdges.length != geometry.card()) throw new IllegalArgumentException();

    this.geometry = geometry;

    this.component = component;

    for (int i = 0; i < geometry.card(); i++) {
      connectors.add(new Connector(this, connectedEdges[i], geometry.getDirections()[i]));
    }
  }

  /**
   * Tells if this tile is powered.
   *
   * @return the power state of this tile.
   */
  public boolean isPowered() {
    return this.component == Component.SOURCE || this.isPowered;
  }

  public void setPowered(boolean state) {
    this.isPowered = state;
  }

  /**
   * Builds a Tile with the given geometry, an empty component and no connectors.
   *
   * @param geometry of the resulting tile.
   */
  public Tile(Geometry geometry) {
    this.geometry = geometry;

    this.component = Component.EMPTY;

    for (int i = 0; i < geometry.card(); i++) {
      connectors.add(new Connector(this, false, geometry.getDirections()[i]));
    }
  }

  /** Rotates given tile by 90 or 60 degrees clockwise depending on its geometry. */
  public void rotateClockwise() {
    Connector current = this.connectors.get(0);
    Connector next = this.connectors.get(1);
    boolean tmp = next.exists();
    next.setExists(current.exists());
    for (int i = 2; i < connectors.size(); i++) {
      current = this.connectors.get(i);
      boolean swapBuffer = current.exists();
      current.setExists(tmp);
      tmp = swapBuffer;
    }
    this.connectors.get(0).setExists(tmp);
  }

  /**
   * Propagates energy through existing connectors to the neighbor tiles. Should only be called on a
   * tile whose isPowered() is true.
   */
  public void propagateEnergyToNeighbors() {
    List<Tile> connectedNeighborsNotPowered = new ArrayList<>();
    for (Connector c : this.connectors) {
      if (c.getNeighbor() == null) continue;
      if (c.hasPath() && !c.getNeighbor().getParentTile().isPowered())
        connectedNeighborsNotPowered.add(c.getNeighbor().getParentTile());
    }
    for (Tile t : connectedNeighborsNotPowered) t.setPowered(true);
    for (Tile t : connectedNeighborsNotPowered) t.propagateEnergyToNeighbors();
  }

  /** Rotates given tile by 90 or 60 degrees counter clockwise depending on its geometry. */
  public void rotateCounterClockwise() {
    for (int i = 0; i < geometry.card() - 1; i++) {
      this.rotateClockwise();
    }
  }

  /** Allows to change this tile's component. */
  public void cycleComponent() {

    int nextIndex =
        (Component.valuesAsList().indexOf(this.component) + 1) % Component.values().length;
    this.component = Component.values()[nextIndex];
  }

  /**
   * Textual representation of this tile connector.
   *
   * @return a String that can be written in a level file.
   */
  private String connectorsToString() {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < connectors.size(); i++) {
      if (connectors.get(i).exists()) res.append(i + " ");
    }
    return res.toString().trim();
  }

  /**
   * Textual representation of this tile.
   *
   * @return a String that can be written in a level file.
   */
  @Override
  public String toString() {
    String existingConnectorsList = this.connectorsToString();
    return String.format(
        existingConnectorsList.length() == 0 ? "%s%s" : "%s %s",
        this.component.toString(),
        existingConnectorsList);
  }

  public String toStringWithEnergy() {
    String existingConnectorsList = this.connectorsToString();
    return String.format(
        existingConnectorsList.length() == 0 ? "%s%s%s" : "%s%s %s",
        this.component.toString(),
        this.isPowered() ? "*" : "",
        existingConnectorsList);
  }

  /**
   * Gives the right index of the connector depending on geometry
   *
   * @param direction the requested connector direction
   * @return the corresponding connector
   */
  public Connector getConnector(Direction direction) {
    for (Connector c : connectors) if (c.getDirection() == direction) return c;
    throw new IllegalArgumentException();
  }

  /**
   * Gives this tile connectors.
   *
   * @return a list containing this tile connectors.
   */
  public List<Connector> getConnectors() {
    return this.connectors;
  }

  @Override
  public Geometry getGeometry() {
    return this.geometry;
  }

  @Override
  public Component getComponent() {
    return this.component;
  }

  @Override
  public boolean[] getConnectorsExist() {
    boolean[] connectorsExist = new boolean[this.geometry.card()];

    for (int i = 0; i < this.geometry.card(); i++)
      connectorsExist[i] = this.connectors.get(i).exists();

    return connectorsExist;
  }

  @Override
  public PowerState getPowerState() {
    return PowerState.fromBoolean(this.isPowered());
  }

  @Override
  public int getNumberOfExistingConnectors() {
    int i = 0;
    for (Connector c : connectors) if (c.exists()) i++;
    return i;
  }

  public void empty() {
    this.component = Component.EMPTY;
    for (Connector c : this.connectors) {
      c.setExists(false);
    }
  }
}
