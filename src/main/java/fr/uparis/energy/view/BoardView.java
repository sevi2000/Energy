package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Geometry;
import fr.uparis.energy.model.Component;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.lang.management.GarbageCollectorMXBean;


public class BoardView extends JPanel implements BoardObserver {
    private BoardObservable boardObservable;

    public BoardView() {
        this.setPreferredSize(new Dimension(800,800));
        System.out.println("built boardView");
        this.setBackground(Color.BLACK);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(), this.getWidth());
        drawTile(g);
        /*if (boardObservable == null)
            return;*/


    }

    private void drawTile(Graphics g) {
        g.drawImage(SpriteBank.getSquare(PowerState.NOT_POWERED),100,100,null);
        g.drawImage(SpriteBank.getComponent(Geometry.SQUARE,PowerState.POWERED, Component.WIFI), 100,100,null);
    }

    @Override
    public void update(BoardObservable boardObservable) {
        this.boardObservable = boardObservable;
        this.repaint();
    }
}
