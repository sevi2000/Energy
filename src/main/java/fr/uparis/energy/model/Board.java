package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
    private List<List<Tile>> tileGrid;

    private Geometry geometry;

    private int width;

    private int height;

    public Board(int width, int height, Geometry geometry) throws InvalidSizeException {
        if (width <= 0 || height <= 0) throw new InvalidSizeException();
        this.width = width;
        this.height = height;
        this.geometry = geometry;
        this.tileGrid = new ArrayList<>();
    }

    public void addRowOnTop() {}

    public void addRowAtBottom(List<Tile> row) throws InvalidSizeException {
        if (this.width != row.size()) throw new InvalidSizeException();
        tileGrid.add(row);
    }

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
        return String.format(
                "%d %d %s", this.tileGrid.size(), this.tileGrid.get(0).size(), geometry.toString());
    }

    private String tileGridToString() {
        String ret = "";
        for (int h = 0; h < this.height; h++) {
            for (int w = 0; w < this.width; w++) {
                ret = ret + tileGrid.get(h).get(w).toString() + " ";
            }
            ret = ret + "\n";
        }
        return ret;
    }

    public void initNeighbors() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Tile t = tileGrid.get(i).get(j);
                Map<Connector, Tile.Direction> existingConnectors = t.getExistingConnectors();
                for (Connector c : existingConnectors.keySet()) {
                    Connector neighbor =
                            switch (existingConnectors.get(c)) {
                                case NORTH -> tileGrid.get(i - 1).get(j).getConnector(Tile.Direction.SOUTH);
                                case NORTH_EAST -> null;
                                case EAST -> null;
                                case SOUTH_EAST -> null;
                                case SOUTH -> tileGrid.get(i + 1).get(j).getConnector(Tile.Direction.NORTH);
                                case SOUTH_WEST -> null;
                                case WEST -> null;
                                case NORTH_WEST -> null;
                            };
                    if (existingConnectors.get(c) == Tile.Direction.NORTH) {
                        c.setNeighbor(tileGrid.get(i - 1).get(j).getConnector(Tile.Direction.SOUTH));
                    } else if (existingConnectors.get(c) == Tile.Direction.SOUTH) {
                        c.setNeighbor(tileGrid.get(i + 1).get(j).getConnector(Tile.Direction.NORTH));
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.config() + "\n" + this.tileGridToString();
    }
}
