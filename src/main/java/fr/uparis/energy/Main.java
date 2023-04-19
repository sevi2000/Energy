package fr.uparis.energy;

import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.LevelConverter;
import fr.uparis.energy.view.EditingLevelView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Energy");
        //frame.getContentPane().add(new MainMenuView(frame));
        frame.getContentPane().add(new EditingLevelView(frame, LevelConverter.getLevelFromResources(8, Level.State.EDITING)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
