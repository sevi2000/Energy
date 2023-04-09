package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.LevelConverter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Bank1View extends JPanel {

    static int selected = -1;

    static JFrame parentWindow;
    private static ArrayList<JLabel> levelButtons = new ArrayList<>();

    Bank1View(JFrame parent) {
        Bank1View.parentWindow = parent;
        ArrayList<Component> components = new ArrayList<>();
        this.setPreferredSize(new Dimension(800, 800));
        components.add(bank1());
        Component box = Box.createRigidArea(new Dimension(0, 100));
        box.setBackground(Color.RED);
        components.add(box);
        components.add(levelsPanel());
        components.add(box);
        components.add(bottomMenu());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        this.setLayout(new GridBagLayout());

        JPanel contentPane = Common.centeredPane(components, 200, 1);
        parent.revalidate();
        add(contentPane, gbc);
    }

    private static JLabel bank1() {
        JLabel res = new JLabel("BANK 1");
        res.setAlignmentX(Component.CENTER_ALIGNMENT);
        // res.setPreferredSize(new Dimension(200, 200));
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);
        res.setAlignmentX(SwingConstants.CENTER);
        res.setAlignmentY(SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 70);
        res.setFont(font);
        res.setBackground(Color.RED);
        return res;
    }

    private static JLabel levelSelectionLabel(String label) {
        JLabel res = new JLabel(label);
        res.setPreferredSize(new Dimension(60, 60));
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
                Bank1View.selected = Integer.parseInt(res.getText());
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
        List<Integer> lvls = LevelConverter.getBank1LevelNumbers();
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
        JLabel play = Common.createButton("Play", new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (selected != -1) {
                    Level lvl = LevelConverter.getLevelFromResources(selected, Level.State.PLAYING);
                    BoardView bv = new BoardView();
                    bv.update((BoardObservable) lvl.getBoard());
                    parentWindow.setContentPane(bv);
                    parentWindow.setVisible(true);
                }
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
        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                parentWindow.setContentPane(new MainMenuView(parentWindow));
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
