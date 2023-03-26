package fr.uparis.energy.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.net.URISyntaxException;

public class MainMenu extends JPanel {

    public MainMenu(JFrame jf) {
        // this.setLayout(new BoxLayout());

        setPreferredSize(new Dimension(800, 800));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, 200)));
        JLabel play = createButton("Play", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JPanel bg = null;
                try {
                    bg = new Bank1();
                    //bg.setBackground(Color.green);
                    jf.setContentPane(bg);
                    jf.setVisible(true);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        this.add(play);
        JLabel quit = createButton("Quit", new MouseAdapter() {
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

    private static JLabel createButton(String title, MouseAdapter l) {
        JLabel res = new JLabel(title);
        res.setOpaque(true);
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);

        Font font = new Font("Arial", Font.BOLD, 40);
        res.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        res.setFont(font);

        res.addMouseListener(l);
        res.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        res.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        return res;
    }


}
