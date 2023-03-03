package fr.uparis.energy.model;

/**
 * Represents a wifi component.
 */
public class WifiComponent extends Component {

    private boolean isPoweredByAnother;

    /**
     * Allows to turn this Wi-Fi hotspot on if at least one Wi-Fi hotspot is powered by a wire.
     * @param state true if the Wi-Fi can be powered.
     */
    public void setPoweredByAnother(boolean state) {
        this.isPoweredByAnother = state;
    }

    /**
     * Textual representation.
     * @return a string to be written in level file.
     */
    @Override
    public String toString() {
        return "W";
    }
}
