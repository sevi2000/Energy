package fr.uparis.energy.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;

public class Common {
    private Common() {}

    public static JLabel createButton(String title, MouseAdapter adapter) {
        JLabel res = new JLabel(title);
        res.setOpaque(true);
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);

        Font font = new Font("Arial", Font.BOLD, 40);
        res.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        res.setFont(font);

        res.addMouseListener(adapter);
        res.addMouseMotionListener(adapter);
        res.setHorizontalAlignment(JLabel.CENTER);
        return res;
    }

    public static JPanel centeredPane(List<Component> children, int border, int col) {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(children.size(), col));
        for (Component component : children) content.add(component);
        content.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        return content;
    }
}
