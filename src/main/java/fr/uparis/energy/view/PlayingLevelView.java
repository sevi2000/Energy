package fr.uparis.energy.view;

import fr.uparis.energy.controller.BoardController;
import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.LevelConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayingLevelView extends JPanel implements BoardObserver{
    private JFrame parentWindow;
    int nextLevel;
    public PlayingLevelView(JFrame jFrame, Level lvl) {
        this.parentWindow = jFrame;
        this.setPreferredSize(new Dimension(800, 800));

        JLabel back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentWindow.setContentPane(new MainMenuView(jFrame));
                parentWindow.setVisible(true);
            }
        });
        
        lvl.start();
        BoardView bv = new BoardView();
        BoardController bc = new BoardController(lvl.getBoard());
        bv.addMouseListener(bc);
        lvl.getBoard().addObserver(bv);
        lvl.getBoard().addObserver(this);
        lvl.getBoard().notifyObservers();
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.nextLevel = lvl.getNumber() + 1;
        
        this.add(bv);
        this.add(back);
    }

    @Override
    public void update(BoardObservable boardObservable) {
        if (boardObservable.isSolved()) {
            int res =JOptionPane.showInternalConfirmDialog(null,"You won","Game over",JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION){
                if (nextLevel < LevelConverter.getBank1LevelNumbers().size() + 1)
                    parentWindow.setContentPane(new PlayingLevelView(parentWindow, LevelConverter.getLevelFromResources(nextLevel, Level.State.PLAYING)));
                else 
                    parentWindow.setContentPane(new MainMenuView(parentWindow));
                parentWindow.setVisible(true);
            }
        }
    }
}
