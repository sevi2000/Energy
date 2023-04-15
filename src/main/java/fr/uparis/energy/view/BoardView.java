package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;
import fr.uparis.energy.model.ReadOnlyTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

        this.drawTile(g, boardObservable.getTileAt(0, 1), 100, 100, 120, 120);
        this.drawTile(g, boardObservable.getTileAt(1, 0), 300, 300, 250, 250);
    }

    private void drawTile(Graphics g, ReadOnlyTile rot, int x, int y, int width, int height) {
        g.drawImage(
                SpriteBank.getShape(this.boardObservable.getGeometry(), rot.getPowerState()),
                x,
                y,
                width,
                height,
                null);

        Image component = SpriteBank.getComponent(
                this.boardObservable.getGeometry(), rot.getPowerState(), rot.getComponent());
        g.drawImage(component, x, y, width, height, null);
        if (rot.getGeometry() == Geometry.SQUARE) {
            if (rot.getComponent() != Component.EMPTY) {
                BufferedImage wire = SpriteBank.getWire(SpriteBank.WireType.SQUARE_SHORT, rot.getPowerState());
                Graphics2D g2d = (Graphics2D) g;

                for (int i = 0; i < rot.getGeometry().card(); i++) {

                    if (rot.getConnectorsExist()[i]) {
                        Common.drawRotatedImage(g, x, y, width, height, 90 * i, wire);
                    }
                }
            } else {
                boolean[] c = rot.getConnectorsExist();
                System.out.println("EMPTY COMPONENT");
                if (c[0] && !c[1] && c[2] && !c[3]) {
                    Common.drawRotatedImage(
                            g,
                            x,
                            y,
                            width,
                            height,
                            0,
                            SpriteBank.getWire(SpriteBank.WireType.SQUARE_LONG, rot.getPowerState()));
                } else if (!c[0] && c[1] && !c[2] && c[3])
                    Common.drawRotatedImage(
                            g,
                            x,
                            y,
                            width,
                            height,
                            90,
                            SpriteBank.getWire(SpriteBank.WireType.SQUARE_LONG, rot.getPowerState()));
                else {
                    if (c[0] && c[1])
                        Common.drawRotatedImage(
                                g,
                                x,
                                y,
                                width,
                                height,
                                0,
                                SpriteBank.getWire(SpriteBank.WireType.SQUARE_CURVED, rot.getPowerState()));
                    
                    if (c[1] && c[2]) 
                        Common.drawRotatedImage(
                                g,
                                x,
                                y,
                                width,
                                height,
                                90,
                                SpriteBank.getWire(SpriteBank.WireType.SQUARE_CURVED, rot.getPowerState()));
                    if (c[2] && c[3])
                        Common.drawRotatedImage(
                                g,
                                x,
                                y,
                                width,
                                height,
                                180,
                                SpriteBank.getWire(SpriteBank.WireType.SQUARE_CURVED, rot.getPowerState()));
                    if (c[0] && c[3])
                        Common.drawRotatedImage(
                                g,
                                x,
                                y,
                                width,
                                height,
                                270,
                                SpriteBank.getWire(SpriteBank.WireType.SQUARE_CURVED, rot.getPowerState()));
                }
            }
        } else {
        }
    }

    /**
     * drawTile(g, x, y, tileWidth, tileHeight, rot):
     * if carré:
     *     dessiner le contour du carré
     *     dessiner le composant
     *     if composant non-vide:
     *         for i in 0..3:
     *             if rot.getConnectorsExist()[i]: faire tourner de 90° le petit fil i fois et le dessiner
     *     else:
     *         e = rot.getConnectorsExist()
     *         if e[0] && !e[1] && e[2] && !e[3]: dessiner le fil nord-sud
     *         else if ouest est !nord !sud: dessiner le fil nord-sud tourné de 90°
     *         else:
     *             if nord et est: dessiner le fil courbé
     *             if est et sud: dessiner le fil courbé tourné de 90°
     *             // idem
     *             // idem
     *
     */
    @Override
    public void update(BoardObservable boardObservable) {
        this.boardObservable = boardObservable;
        this.repaint();
    }
}
