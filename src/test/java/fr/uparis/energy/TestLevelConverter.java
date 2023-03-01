package fr.uparis.energy;

import static org.junit.jupiter.api.Assertions.*;

import fr.uparis.energy.model.InvalidSizeException;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.InvalidLevelException;
import fr.uparis.energy.utils.LevelConverter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @Test
    public void testReadFromAndWriteToFile()
            throws URISyntaxException, IOException, InvalidSizeException, InvalidLevelException {
        URI levelLocation =git
                getClass().getClassLoader().getResource("levels/level11.nrg").toURI();
        Level l = LevelConverter.fileToLevel(levelLocation.toURL(), Level.State.EDITING);
        assertEquals(new String(Files.readAllBytes(Path.of(levelLocation)), StandardCharsets.UTF_8), l.toString());
    }
}
