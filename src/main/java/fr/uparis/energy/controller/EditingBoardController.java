package fr.uparis.energy.controller;

import fr.uparis.energy.model.*;
import fr.uparis.energy.utils.IntPair;
import fr.uparis.energy.view.BoardView;
import fr.uparis.energy.view.Common;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Represents a board controller when we edit a level.
 */
public class EditingBoardController extends MouseAdapter {

    private Board board;

    /**
     * Class constructor.
     * @param board model.
     */
    public EditingBoardController(Board board) {
        this.board = board;
    }

    /**
     * Handles a mouse click on the board.
     * @param e the click event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        BoardView bv = ((BoardView) (e.getSource()));
        IntPair clickedPolygon = Common.getClosestPolygon(bv, e.getX(), e.getY());
        if (isWithinCenterArea(bv, clickedPolygon, e.getX(), e.getY())) {
            board.cycleTileComponent(clickedPolygon);
        } else {
            double angle = getAngle(clickedPolygon, e.getX(), e.getY(), bv);
            toggleConnector(board, clickedPolygon, angle);
        }
        board.propagateEnergy();
        board.notifyObservers();
    }

    private boolean isWithinCenterArea(BoardView bv, IntPair clickedPolygon, int x, int y) {
        IntPair center = Common.invertMapUsingStreams(bv.getCoordinateMap()).get(clickedPolygon);
        double distance = Math.sqrt(Math.pow(center.a - x, 2) + Math.pow(center.b - y, 2));
        return distance < 0.2 * bv.getTileWidth();
    }

    public double getAngle(IntPair clickedPolygon, int x, int y, BoardView bv) {
        IntPair center = Common.invertMapUsingStreams(bv.getCoordinateMap()).get(clickedPolygon);
        return Math.atan2(-(y - center.b), x - center.a);
    }

    private void toggleConnector(Board board, IntPair clickedPolygon, double angle) {
        int conectorsNumber;
        List<Direction> trigonometicaalOrderedDirection;
        double angleOffset = 0;
        if (board.getGeometry() == Geometry.SQUARE) {
            conectorsNumber = Geometry.SQUARE.card();
            trigonometicaalOrderedDirection = Direction4.getTrigonometricalOrderedDirection();
            angleOffset = Math.PI / 4;
        } else {
            conectorsNumber = Geometry.HEXAGON.card();
            trigonometicaalOrderedDirection = Direction6.getTrigonometricalOrderedDirection();
        }
        angle = (angle - angleOffset + 2 * Math.PI) % (2 * Math.PI);
        for (int i = 1; i <= conectorsNumber; i++) {
            if (angle < i * 2 * Math.PI / conectorsNumber) {
                board.getTileAt(clickedPolygon)
                        .getConnector(trigonometicaalOrderedDirection.get(i - 1))
                        .toggleExists();
                break;
            }
        }
    }
}
