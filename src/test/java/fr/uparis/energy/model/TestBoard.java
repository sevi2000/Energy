package fr.uparis.energy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;

import fr.uparis.energy.utils.Bank;
import fr.uparis.energy.utils.InvalidLevelException;
import fr.uparis.energy.utils.LevelConverter;

class TestBoard {

    @Test
    void testBoardSolvedInASolvedLevel() throws InvalidSizeException, IOException, InvalidLevelException {
        // URL levelLocation = getClass().getClassLoader().getResource("levels/level1.nrg");
        Level l = LevelConverter.fileToLevel("levels/level1.nrg", Level.State.PLAYING);
        // l.getBoard().propagateEnergy();
        assertTrue(l.isSolved());
    }

    @Test
    void testBoardSolvedInANonSolvedLevel() throws InvalidSizeException, IOException, InvalidLevelException {
        // URL levelLocation = getClass().getClassLoader().getResource("levels/level3.nrg");
        Level l = LevelConverter.fileToLevel("levels/level3.nrg", Level.State.PLAYING);
        l.start();
        assertFalse(l.isSolved());
    }

    @Test
    void testShuffleWorks() {
        Level l = null;
        Level l2 = null;
        try {
            l2 = LevelConverter.getLevel(4, Level.State.PLAYING, Bank.BANK_1);
            l = LevelConverter.getLevel(4, Level.State.PLAYING, Bank.BANK_1);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        l2.start();
        assertNotEquals(l.toString(), l2.toString());
    }

    @Test
    void testBoardConstructorException() {
        assertThrows(InvalidSizeException.class, () -> new Board(0, 0, Geometry.SQUARE));
    }

    @Test
    void testAddColumnAtLeft() throws InvalidSizeException {
        Board b = new Board(1, 1, Geometry.HEXAGON);
        int expected = b.getWidth() + 1;
        b.addColumnAtLeft();
        assertEquals(expected, b.getWidth());
    }

    @Test
    void testAddRowAtBottom() throws InvalidSizeException {
        Board b = new Board(1, 1, Geometry.HEXAGON);
        int expected = b.getHeight() + 1;
        b.addRowAtBottom();
        assertEquals(expected, b.getHeight());
    }

    @Test
    void testRemoveColumnAtLeft() throws InvalidSizeException {
        Board b = new Board(2, 2, Geometry.HEXAGON);
        int expected = b.getWidth() - 1;
        b.removeColumnAtLeft();
        assertEquals(expected, b.getWidth());
    }

    @Test
    void testRemoveRowAtBottom() throws InvalidSizeException {
        Board b = new Board(2, 2, Geometry.HEXAGON);
        int expected = b.getHeight() - 1;
        b.removeRowAtBottom();
        assertEquals(expected, b.getHeight());
    }
}
