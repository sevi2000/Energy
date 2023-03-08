package fr.uparis.energy.model;

import static org.junit.jupiter.api.Assertions.*;

import fr.uparis.energy.utils.InvalidLevelException;
import fr.uparis.energy.utils.LevelConverter;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.Test;

public class TestBoard {

    @Test
    public void testBoardSolvedInASolvedLevel() throws InvalidSizeException, IOException, InvalidLevelException {
        URL levelLocation = getClass().getClassLoader().getResource("levels/level1.nrg");
        Level l = LevelConverter.fileToLevel(levelLocation, Level.State.PLAYING);
        // l.getBoard().propagateEnergy();
        assertTrue(l.isSolved());
    }

    @Test
    public void testBoardSolvedInANonSolvedLevel() throws InvalidSizeException, IOException, InvalidLevelException {
        URL levelLocation = getClass().getClassLoader().getResource("levels/level3.nrg");
        Level l = LevelConverter.fileToLevel(levelLocation, Level.State.PLAYING);
        l.start();
        assertFalse(l.isSolved());
    }

    @Test
    public void testShuffleWorks() {
        Level l = LevelConverter.getLevelFromResources(4, Level.State.PLAYING);
        Level l2 = LevelConverter.getLevelFromResources(4, Level.State.PLAYING);
        l2.start();
        assertNotEquals(l.toString(), l2.toString());
    }

    @Test
    public void testBoardConstructorException() {
        assertThrows(InvalidSizeException.class, () -> {
            Board board = new Board(0, 0, Geometry.SQUARE);
        });
    }

    @Test
    public void testAddColumnAtLeft() throws InvalidSizeException {
        Board b = new Board(1, 1, Geometry.HEXAGON);
        int expected = b.getWidth() + 1;
        b.addColumnAtLeft();
        assertEquals(expected, b.getWidth());
    }

    @Test
    public void testAddRowAtBottom() throws InvalidSizeException {
        Board b = new Board(1, 1, Geometry.HEXAGON);
        int expected = b.getHeight() + 1;
        b.addRowAtBottom();
        assertEquals(expected, b.getHeight());
    }

    @Test
    public void testRemoveColumnAtLeft() throws InvalidSizeException {
        Board b = new Board(2, 2, Geometry.HEXAGON);
        int expected = b.getWidth() - 1;
        b.removeColumnAtLeft();
        assertEquals(expected, b.getWidth());
    }

    @Test
    public void testRemoveRowAtBottom() throws InvalidSizeException {
        Board b = new Board(2, 2, Geometry.HEXAGON);
        int expected = b.getHeight() - 1;
        b.removeRowAtBottom();
        assertEquals(expected, b.getHeight());
    }
}
