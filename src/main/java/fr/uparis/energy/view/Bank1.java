package fr.uparis.energy.view;

import fr.uparis.energy.utils.LevelConverter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URISyntaxException;
import java.util.List;
import javax.swing.*;

public class Bank1 extends JPanel {
    Bank1() throws URISyntaxException {
        this.setPreferredSize(new Dimension(800, 800));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(bank1());
        this.add(levelsPanel());
    }

    private static JLabel bank1() {
        JLabel res = new JLabel("BANK 1");
        res.setPreferredSize(new Dimension(200, 200));
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);
        res.setAlignmentX(SwingConstants.CENTER);
        res.setAlignmentY(SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 50);
        res.setFont(font);
        return res;
    }

    private static JLabel levelSelectionLabel(String label) {
        JLabel res = new JLabel(label);
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);
        res.setAlignmentX(SwingConstants.CENTER);
        res.setAlignmentY(SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 50);
        res.setFont(font);
        res.setOpaque(true);
        res.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        res.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clikeced");
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        return res;
    }

    public static JPanel levelsPanel() throws URISyntaxException {
        JPanel res = new JPanel();
        res.setPreferredSize(new Dimension(200, 200));
        List<Integer> lvls = LevelConverter.getBank1LevelsNums();
        res.setLayout(new GridLayout(lvls.size() / 5 + 1, 5, 10, 10));
        for (Integer i : lvls) {
            res.add(levelSelectionLabel(i.toString()));
        }
        return res;
    }
}
