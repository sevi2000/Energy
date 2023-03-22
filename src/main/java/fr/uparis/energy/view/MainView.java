package fr.uparis.energy.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainView extends JPanel {
    SpriteBank sb;

    public MainView() throws IOException {
        setPreferredSize(new Dimension(800,800));
        this.sb = new SpriteBank();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        g.drawImage(sb.getNotPoweredLamp(),100,100,null);
        g.drawImage(sb.getPoweredLamp(),0,0,null);
    }
}
