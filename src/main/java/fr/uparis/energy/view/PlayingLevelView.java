package fr.uparis.energy.view;

import fr.uparis.energy.controller.BoardController;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.model.ReadOnlyBoard;
import fr.uparis.energy.utils.Bank;
import fr.uparis.energy.utils.LevelConverter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import javax.swing.*;

/**
 * Represents the screen in which we play a level.
 */
public class PlayingLevelView extends JPanel implements BoardObserver {
    private JFrame parentWindow;
    int nextLevel;
    private Bank bank;

    /**
     * Class constructor.
     * @param jFrame parent window.
     * @param lvl to be played.
     * @param bank of this level.
     */
    public PlayingLevelView(JFrame jFrame, Level lvl, Bank bank) {
        this.bank = bank;
        this.parentWindow = jFrame;
        this.setPreferredSize(Common.FRAME_SIZE);

        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parentWindow.setContentPane(new MainMenuView(jFrame));
                parentWindow.setVisible(true);
            }
        });

        lvl.start();
        BoardView bv = new BoardView();
        BoardController bc = new BoardController(lvl.getBoard());
        bv.addMouseListener(bc);
        lvl.getBoard().addObserver(bv);
        lvl.getBoard().notifyObservers();
        lvl.getBoard().addObserver(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.nextLevel = lvl.getNumber() + 1;

        this.add(bv);
        this.add(back);
    }

    /**
     * Updates the view according to the model.
     * @param rob model from which to get information.
     */
    @Override
    public void update(ReadOnlyBoard rob) {
        if (rob.isSolved()) {
            JOptionPane.showInternalConfirmDialog(null, "You won", "Game over", JOptionPane.PLAIN_MESSAGE);
            if (nextLevel < LevelConverter.getBankLevelNumbers(bank).size() + 1) {
                try {
                    parentWindow.setContentPane(new PlayingLevelView(
                            parentWindow, LevelConverter.getLevel(nextLevel, Level.State.PLAYING, bank), bank));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            } else parentWindow.setContentPane(new MainMenuView(parentWindow));
            parentWindow.setVisible(true);
        }
    }
}
