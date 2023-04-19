package fr.uparis.energy.controller;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.utils.IntPair;
import fr.uparis.energy.view.BoardView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class EditingBoardController extends MouseAdapter {
    
    private Board board;
    
    public EditingBoardController(Board board) {
        this.board = board;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        double minDistance = Double.POSITIVE_INFINITY;
        IntPair clickedPolygon = new IntPair(0,0);
        for(Map.Entry<IntPair,IntPair> entry :((BoardView)(e.getSource())).getCoordinateMap().entrySet()) {
            double distance = Math.sqrt(Math.pow(entry.getKey().a - e.getX(),2) +
                    Math.pow(entry.getKey().b - e.getY(),2));
            
            if (distance < minDistance) {
                minDistance = distance;
                clickedPolygon = entry.getValue();
            }
        }
        board.cycleTileComponent(clickedPolygon);
        board.propagateEnergy();
        board.notifyObservers();
    }
}
