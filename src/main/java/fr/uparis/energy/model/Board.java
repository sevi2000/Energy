package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the board of a level. Any modification made to the board or any of its tiles
 * must be followed by a call to Board.propagateEnergy().
 */
public class Board {

    private final List<List<Tile>> tileGrid;

    private final Geometry geometry;

    /**
     * Builds an empty board with the given dimensions.
     * @param width of the board.
     * @param height of the board.
     * @param geometry of the board.
     * @throws InvalidSizeException if width or height lower than 1.
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
    public void addRowOnTop() {
        List<Tile> newRow = new ArrayList<>(this.getWidth());
        for (int i = 0; i < this.getWidth(); i++) {
            newRow.add(new Tile(this.geometry));
        }
        this.tileGrid.add(0, newRow);

        this.setNeighbors();
    }

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
    public void addRowAtBottom() {
        List<Tile> newRow = new ArrayList<>(this.getWidth());
        for (int i = 0; i < this.getWidth(); i++) {
            newRow.add(new Tile(this.geometry));
        }
        this.tileGrid.add(newRow);

        this.setNeighbors();
    }

    /**
     * Adds a column before the first one.
     */
    public void addColumnAtLeft() {
        for (int i = 0; i < this.getHeight(); i++) {
            this.tileGrid.get(i).add(0, new Tile(this.geometry));
        }

        this.setNeighbors();
    }

    /**
     * Adds a column after the last one.
     */
    public void addColumnAtRight() {
        for (int i = 0; i < this.getHeight(); i++) {
            this.tileGrid.get(i).add(new Tile(this.geometry));
        }

        this.setNeighbors();
    }

    /**
     * Removes the fist row.
     */
    public void removeRowOnTop() {
        this.tileGrid.remove(0);

        this.setNeighbors();
    }

    /**
     * Removes the last row.
     */
    public void removeRowAtBottom() {
        this.tileGrid.remove(this.getHeight() - 1);

        this.setNeighbors();
    }

    /**
     * Removes the first column.
     */
    public void removeColumnAtLeft() {
        for (int i = 0; i < this.getHeight(); i++) {
            this.tileGrid.get(i).remove(0);
        }

        this.setNeighbors();
    }

    /**
     * Removes the last column.
     */
    public void removeColumnAtRight() {
        int currentWidth = this.getWidth();
        for (int i = 0; i < this.getHeight(); i++) {
            this.tileGrid.get(i).remove(currentWidth - 1);
        }

        this.setNeighbors();
    }

    /**
     * Checks if the board is solved.
     * @return true if the board is in a solved state.
     */
    public boolean isSolved() {
        List<Tile> lampTiles = this.getTilesWithComponent("L");
        for (Tile tile : lampTiles) if (!tile.isPowered()) return false;
        return true;
    }

    /**
     * Shuffles the board by rotating each tile randomly.
     */
    public void shuffle() {
        Random rand = new Random();
        for (Tile tile : this.getAllTiles()) {
            int numberOfRotations = rand.nextInt(0, tile.getGeometry().card());
            for (int i = 0; i < numberOfRotations; i++) {
                tile.rotateClockwise();
            }
        }
    }

    /**
     * Propagates the energy to the different tiles.
     */
    public void propagateEnergy() {
        this.turnOffEverything();
        for (Tile t : this.getTilesWithComponent("S")) t.propagateEnergyToNeighbors();

        boolean oneWifiIsPowered = false;
        for (Tile t : this.getTilesWithComponent("W")) if (t.isPowered()) oneWifiIsPowered = true;

        if (oneWifiIsPowered) {
            for (Tile t : this.getTilesWithComponent("W")) t.setPowered(true);
            for (Tile t : this.getTilesWithComponent("W")) t.propagateEnergyToNeighbors();
        }
    }

    /**
     * Turns each tile off except the sources.
     */
    private void turnOffEverything() {
        for (Tile t : this.getAllTiles()) t.setPowered(false);
    }

    private List<Tile> getAllTiles() {
        List<Tile> allTiles = new ArrayList<>();
        for (List<Tile> line : this.tileGrid) allTiles.addAll(line);
        return allTiles;
    }

    private List<Tile> getTilesWithComponent(String component) {
        if (!Component.getKinds().contains(component)) throw new IllegalArgumentException();
        List<Tile> res = new ArrayList<>();
        for (Tile tile : this.getAllTiles()) if (tile.getComponent().toString().equals(component)) res.add(tile);
        return res;
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
