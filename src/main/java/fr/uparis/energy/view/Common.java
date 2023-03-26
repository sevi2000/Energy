package fr.uparis.energy.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Common {
    private Common(){}
    public static JLabel createButton(String title, MouseAdapter l) {
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
