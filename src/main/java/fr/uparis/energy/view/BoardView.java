package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.ReadOnlyTile;
import java.awt.*;
import javax.swing.*;

public class BoardView extends JPanel implements BoardObserver {
    private BoardObservable boardObservable;

    public BoardView() {
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.BLACK);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (this.boardObservable == null) return;

        this.drawTile(g, boardObservable.getTileAt(0, 1), 100, 100);
        this.drawTile(g, boardObservable.getTileAt(1, 0), 300, 300);
    }

    private void drawTile(Graphics g, ReadOnlyTile rot, int x, int y) {
        g.drawImage(SpriteBank.getShape(this.boardObservable.getGeometry(), rot.getPowerState()), x, y, null);

        Image component =
                SpriteBank.getComponent(this.boardObservable.getGeometry(), rot.getPowerState(), rot.getComponent());
        g.drawImage(component, x, y, null);
    }

    @Override
    public void update(BoardObservable boardObservable) {
        this.boardObservable = boardObservable;
        this.repaint();
    }
}
