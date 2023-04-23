package fr.uparis.energy;

import fr.uparis.energy.utils.LevelConverter;
import fr.uparis.energy.view.MainMenuView;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Energy");
        
        frame.getContentPane().add(new MainMenuView(frame));
        //frame.getContentPane().add(new EditingLevelView(frame, LevelConverter.getLevelFromResources(8, Level.State.EDITING)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        LevelConverter.copyBank1Levels();
    }
}
