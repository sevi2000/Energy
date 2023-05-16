package fr.uparis.energy.controller;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.view.BoardView;
import fr.uparis.energy.view.Common;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a board controller when we play.
 */
public class BoardController extends MouseAdapter {

    private Board board;

    /**
     * Class constructor.
     * @param board the model
     */
    public BoardController(Board board) {
        this.board = board;
    }

    /**
     * Handles a mouse click on the board.
     * @param e the click event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            board.rotateTileClockWise(Common.getClosestPolygon((BoardView) e.getSource(), e.getX(), e.getY()));
        else board.rotateTileCounterClockWise(Common.getClosestPolygon((BoardView) e.getSource(), e.getX(), e.getY()));
        board.propagateEnergy();
        board.notifyObservers();
    }
}
