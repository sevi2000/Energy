package fr.uparis.energy.model;

public class Level {
    private int levelNumber;
    private Board board;
    private State state;

    public enum State {
        EDITING,
        PLAYING
    }

    public boolean isSolved() {
        return false;
    }

    public void start() {

    }
}
