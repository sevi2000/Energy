package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;
import fr.uparis.energy.model.ReadOnlyTile;
import fr.uparis.energy.utils.IntPair;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends JPanel implements BoardObserver {
    private BoardObservable rob;
    private Map<IntPair, IntPair> coordinateMap;

    public BoardView() {
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.BLACK);
        this.coordinateMap = new HashMap<>();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.coordinateMap.clear();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        if (rob == null) return;

        int tileWidth;
        if (this.rob.getGeometry() == Geometry.SQUARE)
            tileWidth = this.getWidth() / this.rob.getWidth();
        else
            tileWidth = 4 * this.getWidth() / (3 * this.rob.getWidth() + 1);

        int tileHeight;
        if (this.rob.getGeometry() == Geometry.SQUARE)
            tileHeight = this.getHeight() / this.rob.getHeight();
        else
            tileHeight = (int)Math.floor(this.getHeight() / (this.rob.getHeight() +  0.5));

        // Rectify the tile proportions
        if (this.rob.getGeometry() == Geometry.SQUARE) {
            tileWidth = Math.min(tileWidth, tileHeight);
            tileHeight = tileWidth;
        } else {
            int expectedHeight = (int)(tileWidth * (Math.sqrt(3) / 2));
            if (expectedHeight > tileHeight)
                tileWidth = (int)((2/Math.sqrt(3)) * tileHeight);
            else if (expectedHeight < tileHeight)
                tileHeight = (int)((Math.sqrt(3) / 2) * tileWidth);
        }

        // Center the board
        int boardWidth, boardHeight;
        if (this.rob.getGeometry() == Geometry.SQUARE) {
            boardWidth = tileWidth * this.rob.getWidth();
            boardHeight = tileHeight * this.rob.getHeight();
        } else {
            boardWidth = (tileWidth / 4) * (3 * this.rob.getWidth() + 1);
            boardHeight = (int)(tileHeight * (this.rob.getHeight() + 0.5));
        }
        int startX = (this.getWidth() - boardWidth) / 2;
        int startY = (this.getHeight() - boardHeight) / 2;

        for (int i = 0; i < this.rob.getHeight(); i++) {
            for (int j = 0; j < this.rob.getWidth(); j++) {
                int x, y;
                if (this.rob.getGeometry() == Geometry.SQUARE) {
                    x = j * tileWidth;
                    y = i * tileHeight;
                } else {
                    x = (int)(j * 3.0 / 4.0 * tileWidth);
                    y = (j % 2 == 1) ? i * tileHeight + tileHeight / 2 : i * tileHeight;
                }
                this.coordinateMap.put(new IntPair(startX+x+tileWidth/2,startY+y+tileHeight/2),new IntPair(i,j));
                this.drawTile(g, this.rob.getTileAt(i, j),
                        startX + x, startY + y, tileWidth, tileHeight);
            }
        }
    }

    private void drawTile(Graphics g, ReadOnlyTile rot, int x, int y, int width, int height) {
        g.drawImage(
                SpriteBank.getShape(this.rob.getGeometry(), rot.getPowerState()), x, y,  width, height, null);

        Image component = SpriteBank.getComponent(
                this.rob.getGeometry(), rot.getPowerState(), rot.getComponent());
        g.drawImage(component, x, y, width, height, null);
        if (rot.getGeometry() == Geometry.SQUARE) {
            drawSquareTile(g,rot, x, y, width, height);
        } else {
          drawHexagonTile(g,rot,x,y,width,height);
        }
    }
    
     private void drawSquareTile(Graphics g, ReadOnlyTile rot, int x, int y, int width, int height) {
         if (rot.getComponent() != Component.EMPTY) {
             BufferedImage wire = SpriteBank.getWire(SpriteBank.WireType.SQUARE_SHORT, rot.getPowerState());
             for (int i = 0; i < rot.getGeometry().card(); i++) {

                 if (rot.getConnectorsExist()[i]) {
                     Common.drawRotatedImage(g, x, y, width, height, 90 * i, wire);
                 }
             }
         } else {
             boolean[] c = rot.getConnectorsExist();
             Image straightWire =  SpriteBank.getWire(SpriteBank.WireType.SQUARE_LONG, rot.getPowerState());
             Image curvedWire = SpriteBank.getWire(SpriteBank.WireType.SQUARE_CURVED, rot.getPowerState());


             if (c[0] && !c[1] && c[2] && !c[3]) {
                 Common.drawRotatedImage(g, x, y, width, height, 0, straightWire);
             } else if (!c[0] && c[1] && !c[2] && c[3])
                 Common.drawRotatedImage(g, x, y, width, height, 90, straightWire);
             else {
                 if (c[0] && c[1])
                     Common.drawRotatedImage(g, x, y, width, height, 0,curvedWire);

                 if (c[1] && c[2])
                     Common.drawRotatedImage(g, x, y, width, height, 90, curvedWire);
                 if (c[2] && c[3])
                     Common.drawRotatedImage(g, x, y, width, height, 180, curvedWire);
                 if (c[0] && c[3])
                     Common.drawRotatedImage(g, x, y, width, height, 270, curvedWire);
             }
         }
     }
     
    private  void drawHexagonTile(Graphics g, ReadOnlyTile rot, int x, int y, int width, int height) {
        if (rot.getComponent() != Component.EMPTY) {
            BufferedImage wire = SpriteBank.getWire(SpriteBank.WireType.HEXAGON_SHORT, rot.getPowerState());
            for (int i = 0; i < rot.getGeometry().card(); i++) {

                if (rot.getConnectorsExist()[i]) {
                    Common.drawRotatedImage(g, x, y, width, height, 60 * i, wire);
                }
            }
        } else {
            boolean [] c = rot.getConnectorsExist();
            Image hexagonLongWire = SpriteBank.getWire(SpriteBank.WireType.HEXAGON_LONG,rot.getPowerState());
            Image hexagonCurvedShortWire = SpriteBank.getWire(SpriteBank.WireType.HEXAGON_CURVED_SHORT,rot.getPowerState());
            Image hexagonCurvedLongWire = SpriteBank.getWire(SpriteBank.WireType.HEXAGON_CURVED_LONG,rot.getPowerState());

            if (c[0] && !c[1] && !c[2] && c[3] && !c[4] && !c[5]) Common.drawRotatedImage(g,x,y,width,height,0, hexagonLongWire);
            else if (!c[0] && c[1] && !c[2] && !c[3] && c[4] && !c[5]) Common.drawRotatedImage(g,x,y,width,height,60, hexagonLongWire);
            else if(!c[0] && !c[1] && c[2] && !c[3] && !c[4] && c[5]) Common.drawRotatedImage(g,x,y,width,height,120, hexagonLongWire);
            else {
                for (int i = 0; i < rot.getGeometry().card(); i++) {
                    if (c[i] && c[(i+1) % rot.getGeometry().card()]) Common.drawRotatedImage(g,x,y,width,height,60 * i,hexagonCurvedShortWire);
                    if (c[i] && !c[(i+1) % rot.getGeometry().card()] && c[(i +2) % 6]) Common.drawRotatedImage(g,x,y,width,height,60 * i,hexagonCurvedLongWire);
                }
            }
        }
    }

    @Override
    public void update(BoardObservable boardObservable) {
        this.rob = boardObservable;
        this.repaint();
    }

    public Map<IntPair, IntPair> getCoordinateMap() {
        return this.coordinateMap;
    }
}
