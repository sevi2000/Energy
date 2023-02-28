package fr.uparis.energy.utils;

import fr.uparis.energy.model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelConverter {

    /**
     * Checks if the given array is sorted in ascending order
     * @param list the array to be checked
     * @return true if the given array is sorted
     */
    public static boolean isStrictlySorted(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] >= list[i + 1]) return false;
        }
        return true;
    }

    /**
     * Casts a string representing the connected edges to an integer array
     * @param connectedEdges the string to be cast
     * @return an integer array containing connected edges as integers
     */
    public static int[] toIntArray(String connectedEdges) {
        if (connectedEdges.length() == 0) return new int[0];
        return Arrays.stream(connectedEdges.trim().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    /**
     * Checks if the given array contains the given int
     * @param connectedEdges the array to be checked
     * @param elt the int to be found
     * @return true if the given elt was found
     */
    public static boolean contains(int[] connectedEdges, int elt) {
        for (int connectedEdge : connectedEdges) {
            if (connectedEdge == elt) return true;
        }
        return false;
    }

    /**
     * Transforms the given array to a boolean array
     * @param connectedEdges integer array telling which edges are connected
     * @param geometry the geometry to determine the number of edges
     * @return boolean array containing true at index i if the ith edge is connected
     */
    public static boolean[] getConnectedEdgesAsBoolean(int[] connectedEdges, Geometry geometry) {
        boolean[] res = new boolean[geometry.card()];
        for (int i = 0; i < res.length; i++) {
            res[i] = contains(connectedEdges, i);
        }
        return res;
    }

    /**
     * Prepares a Matcher to validate a line of a level file
     * @param regex to be matched
     * @param line to be checked
     * @return the corresponding matcher object
     */
    public static Matcher getMatcher(String regex, String line) {
        final Pattern validationPattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher validationMatcher = validationPattern.matcher(line);
        return validationMatcher;
    }

    /**
     * Parses a line of a level file
     * @param line to be parsed
     * @param numberOfColumns the number of element the line should contain
     * @param geometry of the level board
     * @return a list representing a level board row
     * @throws InvalidLevelException if the line format is not correct
     */
    private static List<Tile> parseLine(String line, int numberOfColumns, Geometry geometry)
            throws InvalidLevelException {

        final String lineValidationRegex =
                String.format("^([.LSW] ([0-%d] ){0,%d}){%d}$", geometry.card() - 1, geometry.card(), numberOfColumns);
        Matcher validationMatcher = getMatcher(lineValidationRegex, line);
        if (!validationMatcher.find()) throw new InvalidLevelException();

        final String regex = String.format("([.LSW]) (([0-%d] ){0,%d})", geometry.card() - 1, geometry.card());
        final Matcher matcher = getMatcher(regex, line);

        List<Tile> tileRow = new ArrayList<>(numberOfColumns);
        while (matcher.find()) {
            String connectedEdges = matcher.group(2).trim();
            int[] connectedEdgesArray = toIntArray(connectedEdges);
            // Check that group 2 contains integers in strictly ascending order
            if (!isStrictlySorted(connectedEdgesArray)) throw new InvalidLevelException();

            Tile tile = new Tile(geometry, getConnectedEdgesAsBoolean(connectedEdgesArray, geometry), matcher.group(1));
            tileRow.add(tile);
        }

        return tileRow;
    }

    /**
     * Parses each line of a level file to cast it to a level
     * @param path to the level file
     * @param state of the level that will be loaded
     * @return the corresponding level
     * @throws IOError if file opening goes wrong
     * @throws InvalidLevelException if the given file does not respect the format
     * @throws IOException if file opening goes wrong
     * @throws InvalidSizeException if the line length does not fit with the board size
     */
    public static Level fileToLevel(String path, Level.State state)
            throws IOError, InvalidLevelException, IOException, InvalidSizeException {
        URI uri = null;
        try {
            uri = path.toURI();
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        File f = new File(uri);
        List<String> content = Files.readAllLines(Path.of(uri));
        String[] config = content.get(0).trim().split(" ");
        int width = Integer.parseInt(config[1]);
        int height = Integer.parseInt(config[0]);
        Geometry geometry = Geometry.fromString(config[2]);

        // Check that the number of lines in the file is right
        if (content.size() - 1 != height) throw new InvalidLevelException();

        Board board = new Board(width, height, geometry);
        for (String line : content.subList(1, content.size())) {
            board.addRowAtBottom(parseLine(line, width, geometry));
        }
        int levelNumber = Integer.parseInt(f.getName().split("\\.")[0].substring(5, 6));
        return new Level(levelNumber, state, board);
    }

    /**
     * Saves the given level to a file
     * @param level to be saved
     * @throws IOException if file opening goes wrong
     * @throws InvalidLevelException if the level is not correct
     */
    public static void writeLevelToFile(Level level) throws IOException, InvalidLevelException {}
}
