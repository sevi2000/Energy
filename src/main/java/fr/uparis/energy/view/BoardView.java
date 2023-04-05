package fr.uparis.energy.view;

import fr.uparis.energy.model.BoardObservable;
import java.awt.*;
import javax.swing.*;

public class BoardView extends JPanel implements BoardObserver {
    private BoardObservable boardObservable;

    @Override
    public void paintComponent(Graphics g) {

        if (boardObservable == null) return;
    }

    @Override
    public void update(BoardObservable boardObservable) {
        this.boardObservable = boardObservable;
        this.repaint();
    }
}
