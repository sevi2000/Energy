package fr.uparis.energy.model;

public class Level {

    private final int levelNumber;
    private Board board;
    private State state;

    public enum State {
        EDITING,
        PLAYING
    }

    public Level() {
        this.levelNumber = -1;
    }

    public boolean isSolved() {
        return false;
    }

    public void start() {

    }
}
