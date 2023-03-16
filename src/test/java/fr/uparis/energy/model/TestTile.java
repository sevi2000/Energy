package fr.uparis.energy.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestTile {
    @Test
    public void testGeometryConstructor() {
        Tile t = new Tile(Geometry.HEXAGON);
        assertEquals(t.getGeometry(), Geometry.HEXAGON);
        Tile t1 = new Tile(Geometry.SQUARE);
        assertEquals(t1.getGeometry(), Geometry.SQUARE);
    }

    @Test
    public void testRotateClockwiseSquare() {
        boolean[] connectors = {false, false, false, true};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        t.rotateClockwise();
        assertTrue(t.getConnectors().get(0).exists());
    }

    @Test
    public void testRotateCounterClockwiseSquare() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        t.rotateCounterClockwise();
        assertTrue(t.getConnectors().get(3).exists());
    }

    @Test
    public void testRotateClockwiseHexagon() {
        boolean[] connectors = {false, false, false, false, false, true};
        Tile t = new Tile(Geometry.HEXAGON, connectors, Component.EMPTY);
        t.rotateClockwise();
        assertTrue(t.getConnectors().get(0).exists());
    }

    @Test
    public void testRotateCounterClockwiseHexagon() {
        boolean[] connectors = {true, false, false, false, false, false};
        Tile t = new Tile(Geometry.HEXAGON, connectors, Component.WIFI);
        t.rotateCounterClockwise();
        assertTrue(t.getConnectors().get(5).exists());
    }

    @Test
    public void testCycleComponent() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.SOURCE);
        t.cycleComponent();
        assertEquals(Component.LAMP, t.getComponent());
    }

    @Test
    public void testCycleComponentWrap() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        t.cycleComponent();
        assertEquals(Component.SOURCE, t.getComponent());
    }

    @Test
    public void testWrongConnectorsSizeException() {
        boolean[] connectors = {true, false, false, false, false, false};
        assertThrows(IllegalArgumentException.class, () -> new Tile(Geometry.SQUARE, connectors, Component.EMPTY));
    }

    @Test
    public void testGetConnectorException() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        assertThrows(IllegalArgumentException.class, () -> t.getConnector(Direction6.NORTH_EAST));
    }
}
