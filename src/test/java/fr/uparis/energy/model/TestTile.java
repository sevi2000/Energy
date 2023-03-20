package fr.uparis.energy.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestTile {
    @Test
    void testGeometryConstructor() {
        Tile t = new Tile(Geometry.HEXAGON);
        assertEquals(Geometry.HEXAGON, t.getGeometry());
        Tile t1 = new Tile(Geometry.SQUARE);
        assertEquals(Geometry.SQUARE, t1.getGeometry());
    }

    @Test
    void testRotateClockwiseSquare() {
        boolean[] connectors = {false, false, false, true};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        t.rotateClockwise();
        assertTrue(t.getConnectors().get(0).exists());
    }

    @Test
    void testRotateCounterClockwiseSquare() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        t.rotateCounterClockwise();
        assertTrue(t.getConnectors().get(3).exists());
    }

    @Test
    void testRotateClockwiseHexagon() {
        boolean[] connectors = {false, false, false, false, false, true};
        Tile t = new Tile(Geometry.HEXAGON, connectors, Component.EMPTY);
        t.rotateClockwise();
        assertTrue(t.getConnectors().get(0).exists());
    }

    @Test
    void testRotateCounterClockwiseHexagon() {
        boolean[] connectors = {true, false, false, false, false, false};
        Tile t = new Tile(Geometry.HEXAGON, connectors, Component.WIFI);
        t.rotateCounterClockwise();
        assertTrue(t.getConnectors().get(5).exists());
    }

    @Test
    void testCycleComponent() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.SOURCE);
        t.cycleComponent();
        assertEquals(Component.LAMP, t.getComponent());
    }

    @Test
    void testCycleComponentWrap() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        t.cycleComponent();
        assertEquals(Component.SOURCE, t.getComponent());
    }

    @Test
    void testWrongConnectorsSizeException() {
        boolean[] connectors = {true, false, false, false, false, false};
        assertThrows(IllegalArgumentException.class, () -> new Tile(Geometry.SQUARE, connectors, Component.EMPTY));
    }

    @Test
    void testGetConnectorException() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, Component.EMPTY);
        assertThrows(IllegalArgumentException.class, () -> t.getConnector(Direction6.NORTH_EAST));
    }
}
