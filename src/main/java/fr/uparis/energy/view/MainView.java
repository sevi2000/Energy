package fr.uparis.energy.view;

import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class MainView extends JPanel {

    public MainView() throws IOException {
        setPreferredSize(new Dimension(800, 800));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(SpriteBank.getComponent(Geometry.SQUARE, PowerState.NOT_POWERED, Component.WIFI), 100, 100, null);
    }
}
