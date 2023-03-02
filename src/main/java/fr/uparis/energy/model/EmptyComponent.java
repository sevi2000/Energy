package fr.uparis.energy.model;

/**
 * Represents an empty component.
 */
public class EmptyComponent extends Component {

    /**
     * Textual representation.
     * @return a string to be written in level file.
     */
    @Override
    public String toString() {
        return ".";
    }
}
