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

    public static boolean isStrictlySorted(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] >= list[i + 1]) return false;
        }
        return true;
    }

    public static int[] toIntArray(String connectedEdges) {
        if (connectedEdges.length() == 0) return new int[0];
        return Arrays.stream(connectedEdges.trim().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static boolean contains(int[] connectedEdges, int elt) {
        for (int i = 0; i < connectedEdges.length; i++) {
            if (connectedEdges[i] == elt) return true;
        }
        return false;
    }

    public static boolean[] getConnectedEdgesAsBoolean(int[] connectedEdges, Geometry geometry) {
        boolean[] res = new boolean[geometry.card()];
        for (int i = 0; i < res.length; i++) {
            res[i] = contains(connectedEdges, i);
        }
        return res;
    }

    public static Matcher getMatcher(String regex, String line) {
        final Pattern validationPattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher validationMatcher = validationPattern.matcher(line);
        return validationMatcher;
    }

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

    public static Level fileToLevel(URL path, Level.State state)
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

    public static void writeLevelToFile(Level level) throws IOException, InvalidLevelException {
        Files.writeString(Path.of("/tmp/level" + level.getNumber() + ".nrg"), level.toString(), StandardCharsets.UTF_8);
    }
}
