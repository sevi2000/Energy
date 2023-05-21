package fr.uparis.energy.view;

/**
 * Represent the state of a tile.
 */
public enum PowerState {
    /**
     * Electricity does not go through.
     */
    OFF,
    /**
     * Electricity goes through.
     */
    ON;

    /**
     * Static factory.
     * @param state of the tile.
     * @return PowerState.ON if state Power.OFF otherwise
     */
    public static PowerState fromBoolean(boolean state) {
        return state ? PowerState.ON : PowerState.OFF;
    }
}
