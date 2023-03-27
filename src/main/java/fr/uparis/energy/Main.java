package fr.uparis.energy;

import fr.uparis.energy.utils.LevelConverter;
import fr.uparis.energy.view.MainMenu;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        java.util.List<Integer> levels = LevelConverter.getBank1LevelsNums();
        System.out.println(levels);
        JFrame frame = new JFrame("Energy");
        frame.getContentPane().add(new MainMenu(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
