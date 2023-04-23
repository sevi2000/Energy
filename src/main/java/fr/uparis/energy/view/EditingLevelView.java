package fr.uparis.energy.view;

import fr.uparis.energy.controller.EditingBoardController;
import fr.uparis.energy.model.BoardObservable;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.LevelConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class EditingLevelView extends JPanel implements BoardObserver{
    private JFrame parentWindow;
    transient Level lvl;
    JLabel save;
    JLabel back;
    JLabel upArrow;
    JLabel leftArrow;
    JLabel bottomArrow;
    JLabel rightArrow;
    BoardView bv;
    Checkbox ch;
    public EditingLevelView(JFrame jFrame, Level lvl) {
        this.parentWindow = jFrame;
        this.setPreferredSize(new Dimension(800, 800));
        this.lvl = lvl;
        
        this.ch = new Checkbox("Remove");
        this.bv = new BoardView();
        EditingBoardController bc = new EditingBoardController(this.lvl.getBoard());
        bv.addMouseListener(bc);
        this.lvl.getBoard().addObserver(bv);
        this.lvl.getBoard().addObserver(this);
        this.lvl.getBoard().notifyObservers();
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        this.add(bv);
        this.add(bottomPane());
    }
    
    private JPanel bottomPane() {
        JPanel res =  new JPanel();
        res.setLayout(new BoxLayout(res,BoxLayout.X_AXIS));
        res.add(ch);
        JPanel verticalPane = new JPanel();
        this.save = Common.createButton("Save", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lvl.isSolved()) {
                    try {
                        LevelConverter.writeLevelToFile(lvl);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        this.back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentWindow.setContentPane(new Bank1View(parentWindow));
                parentWindow.setVisible(true);
            }
        });
        verticalPane.setLayout(new BoxLayout(verticalPane,BoxLayout.Y_AXIS));
        verticalPane.add(this.save);
        verticalPane.add(this.back);
        JPanel arrowButons  = new JPanel();
        arrowButons.setLayout(new GridLayout(2,2));
        this.upArrow = Common.createButton("⬆️", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeRowOnTop();
                else
                    lvl.getBoard().addRowOnTop();
                bv.update(lvl.getBoard());
            }
        });
        this.leftArrow = Common.createButton("⬅️", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeColumnAtLeft();
                else
                    lvl.getBoard().addColumnAtLeft();
                bv.update(lvl.getBoard());
            }
        });
        
        this.bottomArrow = Common.createButton("⬇️", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeRowAtBottom();
                else
                    lvl.getBoard().addRowAtBottom();
                bv.update(lvl.getBoard());
            }
        });
        
        this.rightArrow = Common.createButton("➡️", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeColumnAtRight();
                else
                    lvl.getBoard().addColumnAtRight();
                bv.update(lvl.getBoard());
            }
        });
        
        arrowButons.add(this.upArrow);
        arrowButons.add(this.leftArrow);
        arrowButons.add(this.bottomArrow);
        arrowButons.add(this.rightArrow);
        res.add(ch);
        res.add(verticalPane);
        res.add(arrowButons);
        return res;
    }

    @Override
    public void update(BoardObservable boardObservable) {
        
    }
}
