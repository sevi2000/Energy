package fr.uparis.energy.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum representing a component held by a Tile.
 */
public enum Component {
    SOURCE("S"),
    LAMP("L"),
    WIFI("W"),
    EMPTY(".");

    private static final Map<String, Component> map = new HashMap<>();

    static {
        for (Component c : values()) {
            map.put(c.toString(), c);
        }
    }

    private final String label;

    Component(String label) {
        this.label = label;
    }

    public static Component getFromLabel(String label) {
        return map.get(label);
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static List<Component> valuesAsList() {
        return Arrays.stream(Component.values()).toList();
    }
}
