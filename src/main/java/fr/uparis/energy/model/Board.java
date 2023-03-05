package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the board of a level.
 */
public class Board {

    private final List<List<Tile>> tileGrid;

    private final Geometry geometry;

    /**
     * Builds an empty board with the given dimensions.
     * @param width of the board.
     * @param height of the board.
     * @param geometry of the board.
     * @throws InvalidSizeException if with or height lower than 0.
     */
    public Board(int width, int height, Geometry geometry) throws InvalidSizeException {
        if (width <= 0 || height <= 0) throw new InvalidSizeException();
        this.geometry = geometry;
        this.tileGrid = new ArrayList<>(height);
        for (int i = 0; i < height; i++) this.tileGrid.add(new ArrayList<>(width));

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) tileGrid.get(i).add(new Tile(this.geometry));
    }

    /**
     * Adds a row before the first one.
     */
    public void addRowOnTop() {}

    /**
     * Sets the given row at the given index.
     * @param lineNumber index for the given row.
     * @param row to be set.
     * @param setNeighbors true if we need to initialize the neighbors.
     * @throws InvalidSizeException if the given row length is not valid.
     */
    public void setRow(int lineNumber, List<Tile> row, boolean setNeighbors) throws InvalidSizeException {
        if (this.getWidth() != row.size()) throw new InvalidSizeException();
        tileGrid.set(lineNumber, row);

        if (setNeighbors) this.setNeighbors();
    }

    /**
     * Adds a row after the last one.
     */
    public void addRowAtBottom() {}

    /**
     * Adds a column before the first one.
     */
    public void addColumnAtLeft() {}

    /**
     * Adds a column after the last one.
     */
    public void addColumnAtRight() {}

    /**
     * Removes the fist row.
     */
    public void removeRowOnTop() {}

    /**
     * Removes the last row.
     */
    public void removeRowAtBottom() {}

    /**
     * Removes the first column.
     */
    public void removeColumnAtLeft() {}

    /**
     * Removes the last column.
     */
    public void removeColumnAtRight() {}

    /**
     * Checks if the board is solved.
     * @return true if the board is in a solved state.
     */
    public boolean isSolved() {
        return false;
    }

    /**
     * Shuffles the board by rotating each tile randomly.
     */
    public void shuffle() {}

    /**
     * Propagates the energy to the different tiles.
     */
    public void propagateEnergy() {}

    /**
     * Turns each tile off except the sources.
     */
    private void turnOffEverything() {}

    /**
     * Gives Wi-Fi components of the board
     * @return a list containing the Wi-Fi components.
     */
    private List<WifiComponent> getWifiComponents() {
        return new ArrayList<>();
    }

    /**
     * String that represents this board configuration.
     * @return string representation of this board's geometry and dimensions.
     */
    private String config() {
        return String.format("%d %d %s", this.getHeight(), this.getWidth(), geometry.toString());
    }

    /**
     * String representation of the board.
     * @return a string representing the tiles of this board.
     */
    private String tileGridToString() {
        String ret = "";
        for (int h = 0; h < this.getHeight(); h++) {
            for (int w = 0; w < this.getWidth(); w++) {
                ret = ret + tileGrid.get(h).get(w).toString() + " ";
            }
            ret = ret + "\n";
        }
        return ret;
    }

    /**
     * Textual representation.
     * @return string ready to be written in a level file.
     */
    @Override
    public String toString() {
        return this.config() + "\n" + this.tileGridToString();
    }

    /**
     * Initializes the neighbor of each connector of each tile.
     */
    public void setNeighbors() {
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                Tile t = tileGrid.get(i).get(j);
                for (Connector c : t.getConnectors()) {
                    try {
                        c.setNeighbor(tileGrid.get(i + c.getDirection().getHeightOffset(j))
                                .get(j + c.getDirection().getWidthOffset())
                                .getConnector(c.getDirection().getOppositeDirection()));
                    } catch (IndexOutOfBoundsException e) {
                        c.setNeighbor(null);
                    }
                }
            }
        }
    }

    /**
     * Returns the width of this board.
     * @return size of a line.
     */
    public int getWidth() {
        return tileGrid.get(0).size();
    }

    /**
     * Returns the height of this board.
     * @return size of a column.
     */
    public int getHeight() {
        return tileGrid.size();
    }
}
