package fr.uparis.energy.view;

import fr.uparis.energy.utils.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class BankSelectionView extends JPanel {
    public BankSelectionView(JFrame jFrame) {
        JLabel bank1 = Common.createButton("Bank 1", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.setContentPane(new BankView(jFrame, Bank.BANK_1));
                jFrame.setVisible(true);
            }
        });
        
        JLabel bank2 = Common.createButton("Bank 2", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.setContentPane(new BankView(jFrame, Bank.BANK_2));
                jFrame.setVisible(true);
            }
        });

        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.setContentPane(new MainMenuView(jFrame));
                jFrame.setVisible(true);
            }
        });

        List<Component> components = new ArrayList<>();
        components.add(titlePane());
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(bank1);
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(bank2);
        components.add(Box.createRigidArea(new Dimension(0, 100)));
        components.add(back);

        this.setPreferredSize(Common.FRAME_SIZE);

        this.setLayout(new GridBagLayout());

        JPanel contentPane = Common.centeredPane(components, 100, 1,100);
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
