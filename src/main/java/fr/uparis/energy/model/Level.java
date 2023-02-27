package fr.uparis.energy.model;

public class Level {

    private final int levelNumber;
    private Board board;
    private State state;

    public enum State {
        EDITING,
        PLAYING
    }

    public Level(int levelNumber, State state, Board board) {
        this.levelNumber = levelNumber;
        this.state = state;
        this.board = board;
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
