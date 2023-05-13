package fr.uparis.energy.view;

import fr.uparis.energy.controller.EditingBoardController;
import fr.uparis.energy.model.Level;
import fr.uparis.energy.model.ReadOnlyBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditingLevelView extends JPanel implements BoardObserver{
    private final String HELP_MESSAGE = """
    Helping
    """;
    private JFrame parentWindow;
    transient Level lvl;
    JLabel back;
    JLabel upArrow;
    JLabel leftArrow;
    JLabel bottomArrow;
    JLabel rightArrow;
    BoardView bv;
    Checkbox ch;
    public EditingLevelView(JFrame jFrame, Level lvl) {
        this.parentWindow = jFrame;
        this.setPreferredSize(Common.FRAME_SIZE);
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
        this.back = Common.createButton("Back", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
                String msg = lvl.isSolved() ?"Sve changes?" : "Exit without saving?";
                Common.showConfirmation("Exit",msg,parentWindow,lvl, false);
        }
        });
        verticalPane.setLayout(new BoxLayout(verticalPane,BoxLayout.Y_AXIS));
        verticalPane.add(Common.createButton("Help", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showInternalConfirmDialog(null, HELP_MESSAGE,
                        "Help",JOptionPane.PLAIN_MESSAGE);
            }
        }));
        verticalPane.add(this.back);
        JPanel arrowButons  = new JPanel();
        arrowButons.setLayout(new GridLayout(2,2));
        this.upArrow = Common.createButton("⬆️", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeRowOnTop();
                else
                    lvl.getBoard().addRowOnTop();
                lvl.getBoard().propagateEnergy();
                bv.update(lvl.getBoard());
            }
        });
        this.leftArrow = Common.createButton("⬅️", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeColumnAtLeft();
                else
                    lvl.getBoard().addColumnAtLeft();
                lvl.getBoard().propagateEnergy();
                bv.update(lvl.getBoard());
            }
        });
        
        this.bottomArrow = Common.createButton("⬇️", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeRowAtBottom();
                else
                    lvl.getBoard().addRowAtBottom();
                lvl.getBoard().propagateEnergy();
                bv.update(lvl.getBoard());
            }
        });
        
        this.rightArrow = Common.createButton("➡️", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ch.getState())
                    lvl.getBoard().removeColumnAtRight();
                else
                    lvl.getBoard().addColumnAtRight();
                lvl.getBoard().propagateEnergy();
                bv.update(lvl.getBoard());
            }
        });
        
        arrowButons.add(this.upArrow);
        arrowButons.add(this.leftArrow);
        arrowButons.add(this.bottomArrow);
        arrowButons.add(this.rightArrow);
        res.add(this.ch);
        res.add(verticalPane);
        res.add(arrowButons);
        return res;
    }

    @Override
    public void update(ReadOnlyBoard rob) {
        
    }
}
