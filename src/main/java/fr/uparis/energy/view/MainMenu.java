package fr.uparis.energy.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URISyntaxException;
import javax.swing.*;

public class MainMenu extends JPanel {
    JFrame parent;

    public MainMenu(JFrame jf) {
        // this.setLayout(new BoxLayout());
        parent = jf;
        setPreferredSize(new Dimension(800, 800));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(titlePane());
        this.add(Box.createRigidArea(new Dimension(0, 200)));
        JLabel play = Common.createButton("Play", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JPanel bg = null;
                try {
                    bg = new Bank1(jf);
                    // bg.setBackground(Color.green);
                    jf.setContentPane(bg);
                    jf.setVisible(true);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.add(play);
        JLabel quit = Common.createButton("Quit", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseExited(e);
                setForeground(Color.RED);
            }
        });
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(quit);
    }

    private JPanel titlePane() {
        JPanel res = new JPanel();
        JLabel title = new JLabel("Energy");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        return res;
    }
}
