package fr.uparis.energy.model;

import java.util.List;

/**
 * Class representing a component held by a Tile.
 */
public abstract class Component {

    /**
     * Representation of a component.
     * @return a String representing this component.
     */
    public abstract String toString();

    public static List<String> getKinds() {
        return List.of(new String[] {"S", "L", "W", "."});
    }
}
