package fr.uparis.energy.controller;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.view.BoardView;
import fr.uparis.energy.view.Common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardController extends MouseAdapter {
    
    private Board board;
    
    public BoardController(Board board) {
        this.board = board;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        board.rotateTileClockWise(Common.getClosestPolygon((BoardView) e.getSource(),e.getX(),e.getY()));
        board.propagateEnergy();
        board.notifyObservers();
    }
}
