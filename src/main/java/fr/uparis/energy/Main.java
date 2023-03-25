package fr.uparis.energy;

import com.diffplug.spotless.cpp.CppDefaults;
import fr.uparis.energy.view.MainMenu;
import fr.uparis.energy.view.MainView;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Energy");
        frame.getContentPane().add(new MainMenu(frame));
        JPanel bg = new JPanel();
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
