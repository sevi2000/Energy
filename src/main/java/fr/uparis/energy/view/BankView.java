package fr.uparis.energy.view;

import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.Bank;
import fr.uparis.energy.utils.LevelConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class BankView extends JPanel {
    private int selectedLevel = -1;
    private final JFrame parentWindow;
    private final List<JLabel> levelButtons = new ArrayList<>();
    
    private Bank bank;
    public BankView(JFrame jFrame, Bank bank) {
        this.bank = bank;
        parentWindow = jFrame;
        List<Component> components = new ArrayList<>();
        this.setPreferredSize(new Dimension(800, 800));
        components.add(bankLabel());
        components.add(this.levelsPanel());
        components.add(Box.createRigidArea(new Dimension(0, 25)));
        components.add(this.bottomMenu());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        this.setLayout(new GridBagLayout());
        int width = switch (bank) {
            case BANK_1 -> 100;
            case BANK_2 -> 50;
        };
        JPanel contentPane = Common.centeredPane(components, width, 1,100);
        jFrame.revalidate();
        add(contentPane, gbc);
    }

    private  JLabel bankLabel() {
        JLabel res = new JLabel();
        switch (this.bank){
            case BANK_1 -> res.setText("Bank 1");
            case BANK_2 -> res.setText("Bank 2");
        }
        
        res.setAlignmentX(Component.CENTER_ALIGNMENT);
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);
        res.setAlignmentX(SwingConstants.CENTER);
        res.setAlignmentY(SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 70);
        res.setFont(font);
        return res;
    }

    private JLabel levelSelectionLabel(String label) {
        JLabel button = Common.createButton(label, null);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedLevel = Integer.parseInt(button.getText());

                resetLevelButtons();
                button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                button.setBackground(Color.lightGray);
            }
        });
        return button;
    }

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

    private void resetLevelButtons() {
        for (JLabel label : this.levelButtons) {
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            label.setBackground(Color.GRAY);
        }
    }

    private JPanel bottomMenu() {
        JPanel res = new JPanel();
        switch (bank) {
            case BANK_1 -> res.setLayout(new GridLayout(1, 4));
            case BANK_2 -> res.setLayout(new GridLayout(1, 4,10,0));
        }
        
        JLabel play = Common.createButton("Play", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLevel != -1) {
                    PlayingLevelView plv = null;
                    try {
                        plv = new PlayingLevelView(parentWindow, LevelConverter.getLevel(selectedLevel, Level.State.PLAYING, bank),bank);
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
            public void mouseClicked(MouseEvent e) {
                parentWindow.setContentPane(new BankSelectionView(parentWindow));
                parentWindow.setVisible(true);
            }
        });
        
        JLabel edit = Common.createButton("Edit", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLevel != -1) {
                    EditingLevelView elv = null;
                    try {
                        elv = new EditingLevelView(parentWindow, LevelConverter.getLevel(selectedLevel, Level.State.PLAYING,bank));
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
            public void mouseClicked(MouseEvent e) {
                if (selectedLevel != -1) {
                    try {
                        Level l = LevelConverter.getLevel(selectedLevel, Level.State.EDITING, bank);
                        l.empty();
                        LevelConverter.writeLevelToFile(l);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        res.add(play);
        
        if (bank == Bank.BANK_2) {
            //res.add(Box.createRigidArea(new Dimension(10, 10)));
            res.add(edit);
            //res.add(Box.createRigidArea(new Dimension(10,10)));
            res.add(empty);
            //res.add(Box.createRigidArea(new Dimension(10,10)));
        } else {
            res.add(Box.createRigidArea(new Dimension(0, 200)));
        }
        res.add(back);

        return res;
    }
}
