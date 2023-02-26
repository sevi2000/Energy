package fr.uparis.energy;

import static org.junit.jupiter.api.Assertions.*;

import fr.uparis.energy.utils.LevelConverter;
import org.junit.jupiter.api.Test;

public class TestLevelConverter {
    @Test
    public void testEmpty() {
        assertTrue(LevelConverter.isStrictlySorted(new int[0]));
    }

    @Test
    public void testNotSorted() {
        assertFalse(LevelConverter.isStrictlySorted(new int[] {5, 3, 8}));
    }
}
