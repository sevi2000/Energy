package fr.uparis.energy;

import fr.uparis.energy.view.MainView;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame =  new JFrame("Energy");
        frame.getContentPane().add(new MainView());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
