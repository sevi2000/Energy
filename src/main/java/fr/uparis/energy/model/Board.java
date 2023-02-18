package fr.uparis.energy.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Tile>> tileGrid;

    private Geometry geometry;
    public Board(int width, int height, Geometry geometry) throws InvalidSizeException {

    }

    public void addRowOnTop() {

    }

    public void addRowAtBottom() {

    }

    public void addColumnAtLeft() {

    }

    public void addColumnAtRight() {

    }

    public void removeRowOnTop() {

    }

    public void removeRowAtBottom() {

    }

    public void removeColumnAtLeft() {

    }

    public void removeColumnAtRight() {

    }

    public boolean isSolved() {
        return false;
    }

    public void shuffle() {

    }

    public void popagateEnergy() {

    }

    private void turnOffEverything() {

    }

    private List<WifiComponent> getWifiComponents() {
        return new ArrayList<>();
    }
}
