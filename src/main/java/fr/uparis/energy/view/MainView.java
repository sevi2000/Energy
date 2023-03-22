package fr.uparis.energy.view;


import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class MainView extends JPanel {
    SpriteBank sb;

    public MainView() throws IOException {
        setPreferredSize(new Dimension(800, 800));
        this.sb = new SpriteBank();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        /*g.drawImage(sb.getSquareNotPoweredLamp(),100,100,null);
        g.drawImage(sb.getSquarePoweredLamp(),400,100,null);
        g.drawImage(sb.getHexagonNotPoweredLamp(),200,100,null);
        g.drawImage(sb.getHexagonPoweredLamp(),300,100,null);*/
        //g.drawImage(sb.getSquarePoweredLamp(),100,100,null);
        g.drawImage(sb.getSprite(Geometry.SQUARE, State.POWERED, Component.LAMP),100,100,null);
    }
}
