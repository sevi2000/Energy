package fr.uparis.energy;

import static org.junit.jupiter.api.Assertions.*;

import fr.uparis.energy.model.*;
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
        Tile t = new Tile(Geometry.SQUARE, connectors, ".");
        t.rotateClockwise(false);
        assertTrue(t.getConnectors().get(0).exists());
    }

    @Test
    public void testRotateCounterClockwiseSquare() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, ".");
        t.rotateCounterClockwise(false);
        assertTrue(t.getConnectors().get(3).exists());
    }

    @Test
    public void testRotateClockwiseHexagon() {
        boolean[] connectors = {false, false, false, false, false, true};
        Tile t = new Tile(Geometry.HEXAGON, connectors, ".");
        t.rotateClockwise(false);
        assertTrue(t.getConnectors().get(0).exists());
    }

    @Test
    public void testRotateCounterClockwiseHexagon() {
        boolean[] connectors = {true, false, false, false, false, false};
        Tile t = new Tile(Geometry.HEXAGON, connectors, ".");
        t.rotateCounterClockwise(false);
        assertTrue(t.getConnectors().get(5).exists());
    }

    @Test
    public void testCycleComponent() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, "S");
        t.cycleComponent();
        assertEquals(LampComponent.class, t.getComponent().getClass());
    }

    @Test
    public void testCycleComponentWrap() {
        boolean[] connectors = {true, false, false, false};
        Tile t = new Tile(Geometry.SQUARE, connectors, ".");
        t.cycleComponent();
        assertEquals(SourceComponent.class, t.getComponent().getClass());
    }
}
