package fr.uparis.energy.model;

public class Level {

    private final int levelNumber;
    private Board board;
    private State state;

    public enum State {
        EDITING,
        PLAYING
    }

    public Level(State state) {
        this.levelNumber = -1;
        this.state = state;
    }

    public boolean isSolved() {
        return false;
    }

    public boolean start() {
        int i = 0;
        while (isSolved() && i < 100) {
            board.shuffle();
            i++;
        }
        return i != 100;
    }
}
