package fr.uparis.energy.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Represents the first screen.
 */
public class MainMenuView extends JPanel {

    /**
     * Class constructor.
     * @param jFrame parent window.
     */
    public MainMenuView(JFrame jFrame) {

        this.setPreferredSize(Common.FRAME_SIZE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Dimension size = new Dimension(Common.FRAME_SIZE.width, 10);
        JLabel play = Common.createButton("Play", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jFrame.setContentPane(new BankSelectionView(jFrame));
                jFrame.setVisible(true);
            }
        });

        JLabel quit = Common.createButton("Quit", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        play.setPreferredSize(new Dimension(200, 100));
        quit.setPreferredSize(new Dimension(200, 100));
        this.add(Common.titlePane());
        this.add(new Box.Filler(
                new Dimension(Common.FRAME_SIZE.width, 50),
                new Dimension(Common.FRAME_SIZE.width, 50),
                new Dimension(Common.FRAME_SIZE.width, 50)));
        this.add(Common.centeredElt(play));
        this.add(Common.centeredElt(quit));
    }
}
