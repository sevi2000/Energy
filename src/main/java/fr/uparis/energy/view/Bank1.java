package fr.uparis.energy.view;

import fr.uparis.energy.utils.LevelConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Bank1 extends JPanel {

    static int selected = -1;

    static JFrame parentWindow;
    private static ArrayList<JLabel> levelButtons = new ArrayList<>();
    Bank1(JFrame parent) {
        Bank1.parentWindow = parent;
        this.setPreferredSize(new Dimension(800, 800));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(bank1());
        this.add(levelsPanel());
        this.add(Box.createRigidArea(new Dimension(0, 200)));
        this.add(bottomMenu());
        this.add(Box.createRigidArea(new Dimension(0, 200)));
    }

    private static JLabel bank1() {
        JLabel res = new JLabel("BANK 1");
        res.setAlignmentX(Component.CENTER_ALIGNMENT);
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
                Bank1.selected = Integer.parseInt(res.getText());
                System.out.println("selected level = " + selected);
                reset();
                if (selected == Integer.parseInt(res.getText())) {
                    res.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    res.setBackground(Color.lightGray);
                }
                super.mouseClicked(e);
            }
        });
        return res;
    }

    public static JPanel levelsPanel() {
        JPanel res = new JPanel();
        res.setPreferredSize(new Dimension(200, 200));
        List<Integer> lvls = LevelConverter.getBank1LevelsNums();
        res.setLayout(new GridLayout(lvls.size() / 5 + 1, 5, 10, 10));
        for (Integer i : lvls) {
            JLabel lvlLabel = levelSelectionLabel(i.toString());
            levelButtons.add(lvlLabel);
            res.add(lvlLabel);
        }
        return res;
    }

    private static void reset() {
        for (JLabel label : levelButtons) {
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            label.setBackground(Color.GRAY);
        }
    }

    public static JPanel bottomMenu() {
        JPanel res = new JPanel();
        res.setLayout(new GridLayout(1, 3));
        JLabel play = Common.createButton("Play", null);
        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                parentWindow.setContentPane(new MainMenu(parentWindow));
                parentWindow.setVisible(true);
            }
        });
        play.setPreferredSize(new Dimension(200, 20));
        back.setPreferredSize(new Dimension(200, 20));
        res.add(play);
        res.add(Box.createRigidArea(new Dimension(0, 200)));
        res.add(back);

        return res;
    }
}
