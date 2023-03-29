package fr.uparis.energy.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class MainMenu extends JPanel {
    JFrame parentWindow;

    public MainMenu(JFrame jf) {
        parentWindow = jf;
        JLabel play = Common.createButton("Play", new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JPanel bg = null;
                bg = new Bank1(jf);
                jf.setContentPane(bg);
                jf.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        JLabel quit = Common.createButton("Quit", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setForeground(Color.RED);
            }
        });

        ArrayList<Component> components = new ArrayList<>();
        components.add(titlePane());
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(play);
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(quit);

        this.setPreferredSize(new Dimension(800, 800));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        this.setLayout(new GridBagLayout());

        JPanel contentPane = Common.centeredPane(components, 100, 1);
        jf.revalidate();
        add(contentPane, gbc);
    }

    private JPanel titlePane() {
        JPanel res = new JPanel();
        JLabel title = new JLabel("Energy");
        title.setFont(new Font("Arial", Font.BOLD, 70));
        res.add(title);
        return res;
    }
}
