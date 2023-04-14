package fr.uparis.energy.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayingLevelView extends JPanel {

    public PlayingLevelView(JFrame jFrame) {
        this.setPreferredSize(new Dimension(800, 800));
        //this.setLayout(new BoxLayout());

        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.setContentPane(new MainMenuView(jFrame));
                jFrame.setVisible(true);
            }
        });


        List<Component> components = new ArrayList<>();
        components.add(back);
        components.add(new BoardView());
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        //components.add();

        this.setPreferredSize(new Dimension(800, 800));

        this.setLayout(new GridBagLayout());

        JPanel contentPane = Common.centeredPane(components, 100, 1);
        jFrame.revalidate();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(contentPane, gbc);

        this.repaint();
    }

}
