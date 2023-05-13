package fr.uparis.energy.controller;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.view.BoardView;
import fr.uparis.energy.view.Common;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BoardController extends MouseAdapter {

  private Board board;

  public BoardController(Board board) {
    this.board = board;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e))
      board.rotateTileClockWise(
          Common.getClosestPolygon((BoardView) e.getSource(), e.getX(), e.getY()));
    else
      board.rotateTileCounterClockWise(
          Common.getClosestPolygon((BoardView) e.getSource(), e.getX(), e.getY()));
    board.propagateEnergy();
    board.notifyObservers();
  }
}
