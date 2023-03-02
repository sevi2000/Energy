package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<List<Tile>> tileGrid;

    private final Geometry geometry;

    // private int width;

    // private int height;

    public Board(int width, int height, Geometry geometry) throws InvalidSizeException {
        if (width <= 0 || height <= 0) throw new InvalidSizeException();
        // this.width = width;
        // this.height = height;
        this.geometry = geometry;
        this.tileGrid = new ArrayList<>(height);
        for (int i = 0; i < height; i++) this.tileGrid.add(new ArrayList<>(width));

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) tileGrid.get(i).add(new Tile(this.geometry));
    }

    public void addRowOnTop() {}

    public void setRow(int lineNumber, List<Tile> row) throws InvalidSizeException {
        if (this.getWidth() != row.size()) throw new InvalidSizeException();
        tileGrid.set(lineNumber, row);
    }

    public void addRowAtBottom() {}

    public void addColumnAtLeft() {}

    public void addColumnAtRight() {}

    public void removeRowOnTop() {}

    public void removeRowAtBottom() {}

    public void removeColumnAtLeft() {}

    public void removeColumnAtRight() {}

    public boolean isSolved() {
        return false;
    }

    public void shuffle() {}

    public void propagateEnergy() {}

    private void turnOffEverything() {}

    private List<WifiComponent> getWifiComponents() {
        return new ArrayList<>();
    }

    private String config() {
        return String.format("%d %d %s", this.getHeight(), this.getWidth(), geometry.toString());
    }

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

    @Override
    public String toString() {
        return this.config() + "\n" + this.tileGridToString();
    }

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

    public int getWidth() {
        return tileGrid.get(0).size();
    }

    public int getHeight() {
        return tileGrid.size();
    }
}
