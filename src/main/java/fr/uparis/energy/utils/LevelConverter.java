package fr.uparis.energy.utils;

import fr.uparis.energy.model.Geometry;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.model.Tile;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelConverter {

    public static boolean isStrictlySorted(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] >= list[i + 1]) return false;
        }
        return true;
    }

    private static List<Tile> parseLine(String line, int numberOfColumns, Geometry geometry)
            throws InvalidLevelException {
        final String lineValidationRegex =
                String.format("^([.LSW] ([0-%d] ){0,%d}){%d}$", geometry.card() - 1, geometry.card(), numberOfColumns);
        System.out.println("lineValidationRegex: " + lineValidationRegex);
        final Pattern validationPattern = Pattern.compile(lineValidationRegex, Pattern.MULTILINE);
        final Matcher validationMatcher = validationPattern.matcher(line);
        if (!validationMatcher.find()) throw new InvalidLevelException();

        final String regex = String.format("([.LSW]) (([0-%d] ){0,%d})", geometry.card() - 1, geometry.card());
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(line);

        List<Tile> tileRow = new ArrayList<Tile>(numberOfColumns);
        while (matcher.find()) {
            String connectedEdges = matcher.group(2).trim();
            int[] connectedEdgesArray =
                    Arrays.stream(connectedEdges.split(" ")).mapToInt(Integer::parseInt).toArray();

            // Check that group 2 contains integers in strictly ascending order
            if (!isStrictlySorted(connectedEdgesArray))
                throw new InvalidLevelException();

            //Tile tile = new Tile(geometry)

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }

        return null;
    }

    public static Level fileToLevel(String path, Level.State state) throws IOError, InvalidLevelException {
        return null;
    }

    public static void writeLevelToFile(Level level) throws IOException, InvalidLevelException {}
}
