package fr.uparis.energy.model;

public class Level {

    private final int number;
    private final Board board;
    private final State state;

    public enum State {
        EDITING,
        PLAYING;
    }

    public Level(int number, State state, Board board) {
        this.number = number;
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

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return this.board.toString();
    }
}
