package fr.uparis.energy.utils;

import static org.junit.jupiter.api.Assertions.*;

import fr.uparis.energy.model.InvalidSizeException;
import fr.uparis.energy.model.Level;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

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
        URL levelLocation = getClass().getClassLoader().getResource("levels/level11.nrg");
        Level l = LevelConverter.fileToLevel(levelLocation, Level.State.PLAYING);
        assertEquals(Files.readString(Path.of(levelLocation.toURI())), l.toString());
    }
}
