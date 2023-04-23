package fr.uparis.energy.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainMenuView extends JPanel {
    public MainMenuView(JFrame jFrame) {
        JLabel play = Common.createButton("Play", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.setContentPane(new BankSelectionView(jFrame));
                jFrame.setVisible(true);
            }
        });

        JLabel quit = Common.createButton("Quit", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        List<Component> components = new ArrayList<>();
        components.add(titlePane());
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(play);
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(quit);

        this.setPreferredSize(new Dimension(800, 800));

        this.setLayout(new GridBagLayout());

        JPanel contentPane = Common.centeredPane(components, 100, 1, 100);
        jFrame.revalidate();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(contentPane, gbc);
    }

    private static JPanel titlePane() {
        JPanel res = new JPanel();
        JLabel title = new JLabel("Energy");
        title.setFont(new Font("Arial", Font.BOLD, 70));
        res.add(title);
        return res;
    }
}
