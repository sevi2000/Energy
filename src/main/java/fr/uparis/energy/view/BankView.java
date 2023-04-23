package fr.uparis.energy.view;

import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.Bank;
import fr.uparis.energy.utils.LevelConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        JPanel contentPane = Common.centeredPane(components, 100, 1);
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
        res.setLayout(new GridLayout(1, 3));
        JLabel play = Common.createButton("Play", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedLevel != -1) {
                   PlayingLevelView plv = new PlayingLevelView(parentWindow, LevelConverter.getLevelFromResources(selectedLevel,Level.State.PLAYING));
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
                    EditingLevelView elv = new EditingLevelView(parentWindow, LevelConverter.getLevelFromResources(selectedLevel,Level.State.PLAYING));
                    parentWindow.setContentPane(elv);
                    parentWindow.setVisible(true);
                }
            }
        });
        res.add(play);
        
        if (bank == Bank.BANK_2) {
            res.add(Box.createRigidArea(new Dimension(0, 50)));
            res.add(edit);
            res.add(Box.createRigidArea(new Dimension(0,50)));
        } else {
            res.add(Box.createRigidArea(new Dimension(0, 200)));
        }
        res.add(back);

        return res;
    }
}
