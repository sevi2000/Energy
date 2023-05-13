package fr.uparis.energy.view;

public enum PowerState {
  OFF,
  ON;

  public static PowerState fromBoolean(boolean state) {
    return state ? PowerState.ON : PowerState.OFF;
  }
}
