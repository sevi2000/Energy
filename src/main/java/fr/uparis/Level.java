package fr.uparis;

public class Level {
    private int levelNumber;
    private Board board;

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
