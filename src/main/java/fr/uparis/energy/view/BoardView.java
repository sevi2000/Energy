package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.ReadOnlyTile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class BoardView extends JPanel implements BoardObserver {
    private BoardObservable boardObservable;

    public BoardView() {
        this.setPreferredSize(new Dimension(800, 800));
        System.out.println("built boardView");
        this.setBackground(Color.BLACK);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getWidth());
        drawTile(g, boardObservable.getTileAt(0, 2), 100, 100);
        drawTile(g, boardObservable.getTileAt(1, 0), 200, 200);
        /*if (boardObservable == null)
        return;*/

    }

    private void drawTile(Graphics g, ReadOnlyTile rt, int i, int j) {
        switch (boardObservable.getGeometry()) {
            case SQUARE -> g.drawImage(SpriteBank.getSquare(rt.getState()), i, j, null);
            case HEXAGON -> {
                g.drawImage(SpriteBank.getHexagon(rt.getState()), i, j, null);
            }
        }

        g.drawImage(
                SpriteBank.getComponent(boardObservable.getGeometry(), rt.getState(), rt.getComponent()), i, j, null);
    }

    @Override
    public void update(BoardObservable boardObservable) {
        this.boardObservable = boardObservable;
        this.repaint();
    }
}
