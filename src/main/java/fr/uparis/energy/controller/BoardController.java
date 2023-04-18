package fr.uparis.energy.controller;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.utils.IntPair;
import fr.uparis.energy.view.BoardView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class BoardController extends MouseAdapter {
    
    private Board board;
    private BoardView boardView;
    
    public BoardController(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        double minDistance = Double.POSITIVE_INFINITY;
        IntPair clickedPolygon = new IntPair(0,0);
        for(Map.Entry<IntPair,IntPair> entry : boardView.getCoordinateMap().entrySet()) {
            double distance = Math.sqrt(Math.pow(entry.getKey().a - e.getX(),2) +
                    Math.pow(entry.getKey().b - e.getY(),2));
            
            if (distance < minDistance) {
                minDistance = distance;
                clickedPolygon = entry.getValue();
            }
                    //e.getX() e.getY() et entry
        }
        board.rotateTileClockWise(clickedPolygon);
        board.propagateEnergy();
        board.notifyObservers();
    }
}
