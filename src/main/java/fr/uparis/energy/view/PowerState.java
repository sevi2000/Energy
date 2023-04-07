package fr.uparis.energy.view;

public enum PowerState {
    POWERED,
    NOT_POWERED;

    public static PowerState fromBoolean(boolean state) {
        return state ? PowerState.POWERED : PowerState.NOT_POWERED;
    }
}
