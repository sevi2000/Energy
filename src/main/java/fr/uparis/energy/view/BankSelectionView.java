package fr.uparis.energy.view;

import fr.uparis.energy.utils.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents the bank selection screen.
 */
public class BankSelectionView extends JPanel {

    /**
     * Class constructor.
     * @param jFrame the parent window.
     */
    public BankSelectionView(JFrame jFrame) {
        this.setPreferredSize(Common.FRAME_SIZE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Dimension size = new Dimension(Common.FRAME_SIZE.width, 10);

        JLabel bank1 = Common.createButton("Bank 1", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jFrame.setContentPane(new BankView(jFrame, Bank.BANK_1));
                jFrame.setVisible(true);
            }
        });

        JLabel bank2 = Common.createButton("Bank 2", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jFrame.setContentPane(new BankView(jFrame, Bank.BANK_2));
                jFrame.setVisible(true);
            }
        });

        JLabel back = Common.createButton("Back  ", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jFrame.setContentPane(new MainMenuView(jFrame));
                jFrame.setVisible(true);
            }
        });
        bank1.setPreferredSize(new Dimension(200, 100));
        bank2.setPreferredSize(new Dimension(200, 100));
        back.setPreferredSize(new Dimension(200, 100));
        this.add(Box.createRigidArea(new Dimension(0, 100)));
        this.add(Common.titlePane());
        this.add(new Box.Filler(
                new Dimension(Common.FRAME_SIZE.width, 50),
                new Dimension(Common.FRAME_SIZE.width, 50),
                new Dimension(Common.FRAME_SIZE.width, 50)));
        this.add(Common.centeredElt(bank1));
        this.add(new Box.Filler(size, size, size));
        this.add(Common.centeredElt(bank2));
        this.add(new Box.Filler(size, size, size));
        this.add(Common.centeredElt(back));
    }
}
