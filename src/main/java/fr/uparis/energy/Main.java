package fr.uparis.energy;

import fr.uparis.energy.model.InvalidSizeException;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.InvalidLevelException;
import fr.uparis.energy.utils.LevelConverter;
import java.io.IOException;

public class Main {
    public static void Test() {}

    public static void main(String[] args) throws InvalidSizeException, IOException, InvalidLevelException {
        Level l = LevelConverter.fileToLevel(
                Main.class.getClassLoader().getResource("levels/level3.nrg"), Level.State.PLAYING);
        LevelConverter.writeLevelToFile(l);
    }
}
