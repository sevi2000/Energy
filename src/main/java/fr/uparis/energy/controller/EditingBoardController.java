package fr.uparis.energy.controller;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.model.Direction4;
import fr.uparis.energy.model.Geometry;
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
        IntPair center = new IntPair(0,0);
        BoardView bv = ((BoardView)(e.getSource()));
        for(Map.Entry<IntPair,IntPair> entry : bv.getCoordinateMap().entrySet()) {
            double distance = Math.sqrt(Math.pow(entry.getKey().a - e.getX(),2) +
                    Math.pow(entry.getKey().b - e.getY(),2));
            
            if (distance < minDistance) {
                minDistance = distance;
                center = entry.getKey();
                clickedPolygon = entry.getValue();
            }
        }
        double distance2 = Math.sqrt(Math.pow(center.a - e.getX(),2) +
                Math.pow(center.b - e.getY(),2));
        if (distance2 < 0.2 * bv.getTileWidth())
            board.cycleTileComponent(clickedPolygon);
        else {
            double angle = Math.atan2(center.b - e.getY(), center.a - e.getX());
            if (board.getGeometry() == Geometry.SQUARE) {
                System.out.println(angle);
                if (angle >= -Math.PI/4 && angle <= Math.PI/4) {
                    
                    board.getTileAt(clickedPolygon).getConnector(Direction4.WEST).toggleExists();
                }
                if (angle >= Math.PI/4 && angle <= 3 * Math.PI/4) {

                    board.getTileAt(clickedPolygon).getConnector(Direction4.NORTH).toggleExists();
                }
                if (angle >= -3 * Math.PI/4 && angle <= -Math.PI/4) {
                    board.getTileAt(clickedPolygon).getConnector(Direction4.SOUTH).toggleExists();
                }
                if (angle >= 3*Math.PI/4 && angle <= Math.PI || angle <= -3*Math.PI/4 && angle >= -Math.PI){
                    board.getTileAt(clickedPolygon).getConnector(Direction4.EAST).toggleExists();
                }
            } else {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        }
        
        board.propagateEnergy();
        board.notifyObservers();


        /**
         * if click à (x,y) dans le polygone (i,j) de centre (cX, cY):
         * if distance entre (x,y) et (cX,cY) < 0,2 * tileWidth:
         *     cycleComponent
         * else:
         *     angle = Math.atan2(cY - y, cX - x)
         *     if angle est entre 0 et pi/3: toggle component nord-est
         *     if angle est entre pi/3 et 2pi/3: toggle component nord
         *     if angle est entre 2pi/3 et pi: toggle nord-ouest
         *     if angle entre 0 et -pi/3: sud-est
         *     if angle entre -pi/3 et -2pi/3: sud
         *     if angle entre -2pi/3 et -pi: sud-ouest
         *
         * if click à (x,y) dans le polygone (i,j) de centre (cX, cY):
         * if distance entre (x,y) et (cX,cY) < 0,2 * tileWidth:
         *     cycleComponent
         * else:
         *     angle = Math.atan2(cY - y, cX - x)
         *     if angle est entre -pi/4 et pi/4: toggle component est
         *     if angle est entre pi/4 et 3pi/4: toggle component nord
         *     if angle entre 3pi/4 et pi ou -3pi/4 et -pi: toggle component ouest
         *     if angle entre -pi/4 et -3pi/4: toggle sud
         */
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double minDistance = Double.POSITIVE_INFINITY;
        for(Map.Entry<IntPair,IntPair> entry :((BoardView)(e.getSource())).getCoordinateMap().entrySet()) {
            double distance = Math.sqrt(Math.pow(entry.getKey().a - e.getX(),2) +
                    Math.pow(entry.getKey().b - e.getY(),2));

            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        board.propagateEnergy();
        board.notifyObservers();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    
    @Override
    public void mousePressed(MouseEvent e){
       // firstPolygon = 
    }
}
