package fr.uparis.energy.model;

import fr.uparis.energy.utils.IntPair;
import fr.uparis.energy.view.BoardObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the board of a level. Any modification made to the board or any of its tiles
 * must be followed by a call to Board.propagateEnergy(). A board is forced to be at least 1x1.
 */
public class Board implements BoardObservable {

    private final List<List<Tile>> tileGrid;

    private Geometry geometry;

    private final Random rand = new Random();

    private final List<BoardObserver> boardObservers = new ArrayList<>();

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
        if (this.getHeight() == 1) return;
        this.tileGrid.remove(0);
        this.setNeighbors();
    }

    /**
     * Removes the last row.
     */
    public void removeRowAtBottom() {
        if (this.getHeight() == 1) return;
        this.tileGrid.remove(this.getHeight() - 1);
        this.setNeighbors();
    }

    /**
     * Removes the first column.
     */
    public void removeColumnAtLeft() {
        if (this.getWidth() == 1) return;
        for (int i = 0; i < this.getHeight(); i++) {
            this.tileGrid.get(i).remove(0);
        }

        this.setNeighbors();
    }

    /**
     * Removes the last column.
     */
    public void removeColumnAtRight() {
        if (this.getWidth() == 1) return;
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
    @Override
    public boolean isSolved() {
        List<Tile> lampTiles = this.getTilesWithComponent(Component.LAMP);
        for (Tile tile : lampTiles) if (!tile.isPowered()) return false;
        return true;
    }

    /**
     * Shuffles the board by rotating each tile randomly.
     */
    public void shuffle() {
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
        for (Tile t : this.getTilesWithComponent(Component.SOURCE)) t.propagateEnergyToNeighbors();

        boolean oneWifiIsPowered = false;
        for (Tile t : this.getTilesWithComponent(Component.WIFI)) if (t.isPowered()) oneWifiIsPowered = true;

        if (oneWifiIsPowered) {
            for (Tile t : this.getTilesWithComponent(Component.WIFI)) t.setPowered(true);
            for (Tile t : this.getTilesWithComponent(Component.WIFI)) t.propagateEnergyToNeighbors();
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

    private List<Tile> getTilesWithComponent(Component component) {
        if (component == null) throw new IllegalArgumentException();
        List<Tile> res = new ArrayList<>();
        for (Tile tile : this.getAllTiles()) if (tile.getComponent() == component) res.add(tile);
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
        StringBuilder ret = new StringBuilder();
        for (int h = 0; h < this.getHeight(); h++) {
            for (int w = 0; w < this.getWidth(); w++) {
                ret.append(tileGrid.get(h).get(w).toString() + " ");
            }
            ret.append("\n");
        }
        return ret.toString();
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
    @Override
    public int getWidth() {
        return tileGrid.get(0).size();
    }

    /**
     * Returns the height of this board.
     * @return size of a column.
     */
    @Override
    public int getHeight() {
        return tileGrid.size();
    }

    private String tileGridToStringWithEnergy() {
        StringBuilder ret = new StringBuilder();
        for (int h = 0; h < this.getHeight(); h++) {
            for (int w = 0; w < this.getWidth(); w++) {
                ret.append(tileGrid.get(h).get(w).toStringWithEnergy() + " ");
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public String toStringWithEnergy() {
        return this.config() + "\n" + this.tileGridToStringWithEnergy();
    }

    @Override
    public ReadOnlyTile getTileAt(int i, int j) {
        if (!(0 <= i && i < this.getHeight())) throw new IllegalArgumentException();
        if (!(0 <= j && j < this.getWidth())) throw new IllegalArgumentException();
        return this.tileGrid.get(i).get(j);
    }

    @Override
    public void addObserver(BoardObserver o) {
        this.boardObservers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (BoardObserver o : this.boardObservers) o.update(this);
    }

    @Override
    public Geometry getGeometry() {
        return this.geometry;
    }
    
    @Override
    public void rotateTileClockWise(IntPair clickedPolygon) {
        this.tileGrid.get(clickedPolygon.a).get(clickedPolygon.b).rotateClockwise();
    }

    @Override
    public void cycleTileComponent(IntPair clickedPolygon) {
        this.tileGrid.get(clickedPolygon.a).get(clickedPolygon.b).cycleComponent();
    }
    
    public Tile getTileAt(IntPair position) {
        return tileGrid.get(position.a).get(position.b);
    }
    public void empty () {
        for (List<Tile> line: tileGrid)
            for (Tile t: line)
                t.empty();
    }

    public void toggleGeometry() {
        this.empty();
        this.geometry = this.geometry.opposite();
    }
}
