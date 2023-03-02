package fr.uparis.energy.model;

/**
 * Represents a source (i.e a components that is always powered.
 */
public class SourceComponent extends Component {

    /**
     * Textual representation.
     * @return a String that can be written in a level file.
     */
    @Override
    public String toString() {
        return "S";
    }
}
