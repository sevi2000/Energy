package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;
import fr.uparis.energy.model.ReadOnlyTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardView extends JPanel implements BoardObserver {
    private BoardObservable rob;

    public BoardView() {
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.BLACK);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.fillRect(0,0,getWidth(),getHeight());
        int tileWidth = this.getWidth() / rob.getWidth();
        int tileHeight = 0;
        if (tileWidth > SpriteBank.SQUARE_IMAGE_WIDTH)
            tileWidth = SpriteBank.SQUARE_IMAGE_WIDTH;
        if (this.rob.getGeometry() == Geometry.SQUARE) {
            tileHeight = this.getHeight() / rob.getHeight();
            if (tileHeight > SpriteBank.SQUARE_IMAGE_HEIGHT) 
                tileHeight = SpriteBank.SQUARE_IMAGE_HEIGHT;
        } else {
            tileHeight = (int)Math.floor(this.getHeight() / (rob.getHeight() +  0.5));
            if (tileHeight > SpriteBank.HEXAGON_IMAGE_HEIGHT);tileHeight = SpriteBank.HEXAGON_IMAGE_HEIGHT;
        }

        for (int i = 0; i < rob.getHeight(); i++) {
            for (int j = 0; j < rob.getWidth(); j++) {
                int x;
                if (rob.getGeometry() == Geometry.SQUARE || j == 0) x = j * tileWidth;
                else{
                    System.out.println("ELSE");
                    System.out.println(j * tileWidth);
                    x = j * (tileWidth - 28); 
                }  
                int y;
                if (rob.getGeometry() == Geometry.HEXAGON && j % 2 == 1) y = i * tileHeight + tileHeight / 2;
                else y = i * tileHeight;
                if (i == 0) {
                    System.out.format(" j = %d x = %d\n", j, x);
                }
                    
                this.drawTile(g,rob.getTileAt(i,j),x,y,tileWidth,tileHeight);
            }
            
        }
    }

    private void drawTile(Graphics g, ReadOnlyTile rot, int x, int y, int width, int height) {
        g.drawImage(
                SpriteBank.getShape(this.rob.getGeometry(), rot.getPowerState()),
                x,
                y,
                width,
                height,
                null);

        Image component = SpriteBank.getComponent(
                this.rob.getGeometry(), rot.getPowerState(), rot.getComponent());
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
            if (rot.getComponent() != Component.EMPTY) {
                BufferedImage wire = SpriteBank.getWire(SpriteBank.WireType.HEXAGON_SHORT, rot.getPowerState());
                Graphics2D g2d = (Graphics2D) g;

                for (int i = 0; i < rot.getGeometry().card(); i++) {

                    if (rot.getConnectorsExist()[i]) {
                        Common.drawRotatedImage(g, x, y, width, height, 60 * i, wire);
                    }
                }
            } else {
                boolean [] c = rot.getConnectorsExist();
                if (c[0] && !c[1] && !c[2] && c[3] && !c[4] && !c[5]) Common.drawRotatedImage(g,x,y,width,height,0,SpriteBank.getWire(SpriteBank.WireType.HEXAGON_LONG,rot.getPowerState()));
                else if (!c[0] && c[1] && !c[2] && !c[3] && c[4] && !c[5]) Common.drawRotatedImage(g,x,y,width,height,60,SpriteBank.getWire(SpriteBank.WireType.HEXAGON_LONG,rot.getPowerState()));
                else if(!c[0] && !c[1] && c[2] && !c[3] && !c[4] && c[5]) Common.drawRotatedImage(g,x,y,width,height,120,SpriteBank.getWire(SpriteBank.WireType.HEXAGON_LONG,rot.getPowerState()));
                else {
                    for (int i = 0; i < rot.getGeometry().card(); i++) {
                        if (c[i] && c[(i+1) % rot.getGeometry().card()]) Common.drawRotatedImage(g,x,y,width,height,60 * i,SpriteBank.getWire(SpriteBank.WireType.HEXAGON_CURVED_SHORT,rot.getPowerState()));
                        if (c[i] && !c[(i+1) % rot.getGeometry().card()] && c[(i +2) % 6]) Common.drawRotatedImage(g,x,y,width,height,60 * i,SpriteBank.getWire(SpriteBank.WireType.HEXAGON_CURVED_LONG,rot.getPowerState()));
                    }
                }
            }
        }
    }
    /* private void drawSquareTile();
    private  void drawHexagonTile();
      */  
    @Override
    public void update(BoardObservable boardObservable) {
        this.rob = boardObservable;
        this.repaint();
    }
}
