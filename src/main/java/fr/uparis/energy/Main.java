package fr.uparis.energy;

import fr.uparis.energy.utils.LevelConverter;
import fr.uparis.energy.view.MainMenu;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        java.util.List<Integer> levels = LevelConverter.getBank1LevelsNums();
        System.out.println(levels);
        JFrame frame = new JFrame("Energy");
        frame.getContentPane().add(new MainMenu(frame));
        JPanel bg = new JPanel();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
