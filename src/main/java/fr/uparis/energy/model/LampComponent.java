package fr.uparis.energy.model;

/**
 * Represent a lamp component. A level is won when all its lamps are powered.
 */
public class LampComponent extends Component {

    /**
     * Textual representation.
     * @return a string to be written in level file.
     */
    @Override
    public String toString() {
        return "L";
    }
}
