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
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils class to store and retrieve a level from a file.
 */
public class LevelConverter {
    

    /**
     * Class constructor.
     * This constructor is private to prevent creating a LevelConverter.
     */
    
    public static final String energyPath = new StringBuilder().
            append(System.getProperty("user.home")).
            append(System.getProperty("file.separator")).
            append(".energy").toString(); 
    private LevelConverter() {}

    /**
     * Checks if the given array is sorted in ascending order
     *
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
     *
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
     *
     * @param array the array to be checked
     * @param element the int to be found
     * @return true if the given elt was found
     */
    public static boolean contains(int[] array, int element) {
        for (int e : array) {
            if (e == element) return true;
        }
        return false;
    }

    /**
     * Transforms the given array to a boolean array
     *
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
     *
     * @param regex to be matched
     * @param line to be checked
     * @return the corresponding matcher object
     */
    public static Matcher getMatcher(String regex, String line) {
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        return pattern.matcher(line);
    }

    /**
     * Parses a line of a level file
     *
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

            Tile tile = new Tile(
                    geometry,
                    getConnectedEdgesAsBoolean(connectedEdgesArray, geometry),
                    Component.getFromLabel(matcher.group(1)));
            tileRow.add(tile);
        }

        return tileRow;
    }

    /**
     * Parses each line of a level file to cast it to a level
     *
     * @param path to the level file
     * @param state of the level that will be loaded
     * @return the corresponding level
     * @throws IOError if file opening goes wrong
     * @throws InvalidLevelException if the given file does not respect the format
     * @throws IOException if file opening goes wrong
     * @throws InvalidSizeException if the line length does not fit with the board size
     */
    public static Level fileToLevel(URL path, Level.State state)
            throws InvalidLevelException, IOException, InvalidSizeException {
        URI uri = null;
        try {
            uri = path.toURI();
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        File f = new File(uri);
        List<String> content = Files.readAllLines(Path.of(uri));

        // Parse the first line
        String[] config = content.get(0).trim().split(" ");
        int width = Integer.parseInt(config[1]);
        int height = Integer.parseInt(config[0]);
        Geometry geometry = Geometry.fromString(config[2]);

        // Check that the number of lines in the file is right
        if (content.size() - 1 != height) throw new InvalidLevelException();

        Board board = new Board(width, height, geometry);
        List<String> lines = content.subList(1, content.size());
        for (int i = 0; i < lines.size(); i++) {
            board.setRow(i, parseLine(lines.get(i), width, geometry), false);
        }
        board.setNeighbors();

        Matcher levelNumberMatcher = getMatcher("^level([0-9]+)\\.nrg$", f.getName());
        if (!levelNumberMatcher.find()) throw new IllegalArgumentException();
        int levelNumber = Integer.parseInt(levelNumberMatcher.group(1));

        return new Level(levelNumber, state, board);
    }

    public static Level getLevelFromResources(int number, Level.State state) {
        URL levelLocation = LevelConverter.class.getClassLoader().getResource("levels/level" + number + ".nrg");
        if (levelLocation == null) throw new IllegalArgumentException();

        Level l = null;
        try {
            l = fileToLevel(levelLocation, state);
        } catch (Exception e) {
            System.exit(1);
        }

        return l;
    }

    /**
     * Saves the given level to a file
     *
     * @param level to be saved
     * @throws IOException if file opening goes wrong
     */
    public static void writeLevelToFile(Level level) throws IOException {
        
        File directory = new File(energyPath);
        Files.writeString(
                Path.of(new StringBuilder().
                                append(energyPath).
                                append(System.getProperty("file.separator")).
                                append("level").
                                append(level.getNumber()).
                                append( ".nrg").toString()),
                level.toString(),
                StandardCharsets.UTF_8);
    }

    private static String extractNumber(String levelName) {
        Matcher matcher = LevelConverter.getMatcher("^level(\\d+)\\.nrg$", levelName);
        if (!matcher.find()) throw new IllegalArgumentException();
        return matcher.group(1);
    }

    public static List<Integer> getBankLevelNumbers(Bank bank) {
        List<Integer> res = new ArrayList<>();
        ClassLoader cl = LevelConverter.class.getClassLoader();
        URL path = cl.getResource("levels");
        if (path == null) throw new IllegalStateException();
        String dir = switch (bank) {
            case BANK_1 -> path.getPath();
            case BANK_2 -> System.getProperty("user.home") + System.getProperty("file.separator") + ".energy";
        };
        File levels = new File(dir);
        File[] list = levels.listFiles();
        if (list == null) throw new IllegalStateException();
        for (File f : list) {
            res.add(Integer.parseInt(extractNumber(f.getName())));
        }
        Collections.sort(res);
        return res;
    }
    
    public static void copyBank1Levels() throws IOException {
        File energyDir = new File(Path.of(energyPath).toUri());
        if (energyDir.exists()) return;
        energyDir.mkdir();
        for (int i = 1; i < getBankLevelNumbers(Bank.BANK_1).size(); i++) {
            Path original = Path.of(Objects.requireNonNull(LevelConverter.
                    class.
                    getClassLoader().
                    getResource("levels/level" + i + ".nrg")).
                    getPath());
            Path copied = Path.of((energyDir+System.getProperty("file.separator")+"level"+i+".nrg"));
            Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
        }
        
    }
}
