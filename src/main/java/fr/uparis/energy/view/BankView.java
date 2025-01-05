package fr.uparis.energy.view;

import fr.uparis.energy.model.Board;
import fr.uparis.energy.model.Geometry;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.Bank;
import fr.uparis.energy.utils.LevelConverter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Represents the level selection screen.
 */
public class BankView extends JPanel {
    private int selectedLevel = -1;
    private final JFrame parentWindow;
    private final List<JLabel> levelButtons = new ArrayList<>();

    private Bank bank;

    /**
     * Class constructor.
     *
     * @param jFrame parent window.
     * @param bank to be displayed.
     */
    public BankView(JFrame jFrame, Bank bank) {
        this.bank = bank;
        parentWindow = jFrame;
        this.setPreferredSize(Common.FRAME_SIZE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.bankLabel());
        this.add(this.levelsPanel());
        Dimension minSize = new Dimension(5, 100);
        Dimension prefSize = new Dimension(5, 100);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);
        this.add(new Box.Filler(minSize, prefSize, maxSize));
        this.add(this.bottomMenu());
        if (bank == Bank.BANK_1) this.add(new Box.Filler(minSize, prefSize, maxSize));
    }

    /**
     * Gives the title of the screen.
     * @return a JPanel holding the appropriate title.
     */
    private JPanel bankLabel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel res = new JLabel();
        switch (this.bank) {
            case BANK_1 -> res.setText("Bank 1");
            case BANK_2 -> res.setText("Bank 2");
        }

        res.setAlignmentX(Component.CENTER_ALIGNMENT);
        res.setForeground(Color.BLACK);
        res.setAlignmentX(SwingConstants.CENTER);
        res.setAlignmentY(SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 70);
        res.setFont(font);
        panel.add(res);
        return panel;
    }

    /**
     * Prepares a label representing a level number.
     * @param label content.
     * @return a JLabel with a level number.
     */
    private JLabel levelSelectionLabel(String label) {
        JLabel button = Common.createButton(label, null);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedLevel = Integer.parseInt(button.getText());

                resetLevelButtons();
                button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                button.setBackground(Color.lightGray);
            }
        });
        return button;
    }

    /**
     * Level selection panel.
     * @return a JPanel containing buttons for each level.
     */
    private JPanel levelsPanel() {
        JPanel res = new JPanel();
        res.setPreferredSize(new Dimension(200, 200));
        List<Integer> lvls = LevelConverter.getBankLevelNumbers(bank);
        res.setLayout(new GridLayout(lvls.size() / 5 + 1, 5, 10, 10));
        for (Integer i : lvls) {
            JLabel lvlLabel = levelSelectionLabel(i.toString());
            this.levelButtons.add(lvlLabel);
            res.add(lvlLabel);
        }
        return res;
    }

    /**
     * Resets other buttons state when we select a level.
     */
    private void resetLevelButtons() {
        for (JLabel label : this.levelButtons) {
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            label.setBackground(Color.GRAY);
        }
    }

    /**
     * Represents the menu at the bottom of the screen.
     * @return a JPanel containing the necessary buttons.
     */
    private JPanel bottomMenu() {
        JPanel res = new JPanel();
        switch (bank) {
            case BANK_1 -> res.setLayout(new GridLayout(1, 4));
            case BANK_2 -> res.setLayout(new GridLayout(2, 3, 5, 5));
        }

        JLabel play = Common.createButton("Play", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedLevel != -1) {
                    PlayingLevelView plv = null;
                    try {
                        plv = new PlayingLevelView(
                                parentWindow, LevelConverter.getLevel(selectedLevel, Level.State.PLAYING, bank), bank);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    parentWindow.setContentPane(plv);
                    parentWindow.setVisible(true);
                }
            }
        });

        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parentWindow.setContentPane(new BankSelectionView(parentWindow));
                parentWindow.setVisible(true);
            }
        });

        JLabel edit = Common.createButton("Edit", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedLevel != -1) {
                    EditingLevelView elv = null;
                    try {
                        elv = new EditingLevelView(
                                parentWindow, LevelConverter.getLevel(selectedLevel, Level.State.PLAYING, bank));
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    parentWindow.setContentPane(elv);
                    parentWindow.setVisible(true);
                }
            }
        });

        JLabel empty = Common.createButton("Empty", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedLevel != -1) {
                    try {
                        Level l = LevelConverter.getLevel(selectedLevel, Level.State.EDITING, bank);
                        l.empty();
                        Common.showConfirmation("Empty", "Would you want to empty ?", parentWindow, l, true);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        JLabel toggleGeometry = Common.createButton("Toggle Geometry", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedLevel != -1) {
                    try {
                        Level l = LevelConverter.getLevel(selectedLevel, Level.State.EDITING, bank);
                        l.toggleGeometry();
                        Common.showConfirmation(
                                "Geometry", "Would you want to change geometry?", parentWindow, l, true);
                        // System.out.println("WROTE LVL");
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JLabel addLevel = Common.createButton("Add Level", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int newLevelNumber =
                        LevelConverter.getBankLevelNumbers(Bank.BANK_2).size() + 1;
                if (newLevelNumber >= 31) return;
                try {
                    Level lvl = new Level(newLevelNumber, Level.State.EDITING, new Board(3, 3, Geometry.SQUARE));
                    LevelConverter.writeLevelToFile(lvl);
                } catch (Exception e1) {
                    throw new RuntimeException();
                }
                parentWindow.setContentPane(new BankView(parentWindow, Bank.BANK_2));
                parentWindow.setVisible(true);
            }
        });
        if (bank == Bank.BANK_1) {
            play.setPreferredSize(new Dimension(10, 10));
            back.setPreferredSize(new Dimension(10, 10));
        }
        res.add(play);

        if (bank == Bank.BANK_2) {
            Font font = new Font("Arial", Font.BOLD, 20);

            edit.setFont(font);
            res.add(edit);

            empty.setFont(font);
            res.add(empty);

            toggleGeometry.setFont(font);
            res.add(toggleGeometry);
            addLevel.setFont(font);
            res.add(addLevel);
            play.setFont(font);
            back.setFont(font);
        } else {
            res.add(Box.createRigidArea(new Dimension(0, 200)));
        }
        res.add(back);

        return res;
    }
}
