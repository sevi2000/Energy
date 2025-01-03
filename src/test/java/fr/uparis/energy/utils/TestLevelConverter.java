package fr.uparis.energy.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import fr.uparis.energy.model.InvalidSizeException;

class TestLevelConverter {
    @Test
    void testEmptyIsSorted() {
        assertTrue(LevelConverter.isStrictlySorted(new int[0]));
    }

    @Test
    void testNotSorted() {
        assertFalse(LevelConverter.isStrictlySorted(new int[] {5, 3, 8}));
    }

    @Test
    void testFileToLevel() throws URISyntaxException, IOException, InvalidSizeException, InvalidLevelException {
        // URL levelLocation = getClass().getClassLoader().getResource("levels/level11.nrg");
        // Level l = LevelConverter.fileToLevel("levels/level11.nrg", Level.State.PLAYING);
        // assertEquals(Files.readString(Path.of(levelLocation.toURI())), l.toString());
    }
}
