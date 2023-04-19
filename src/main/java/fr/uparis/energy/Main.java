package fr.uparis.energy;

import fr.uparis.energy.view.MainMenuView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Energy");
        frame.getContentPane().add(new MainMenuView(frame));
        //frame.getContentPane().add(new PlayingLevelView(frame, LevelConverter.getLevelFromResources(1,Level.State.PLAYING)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
