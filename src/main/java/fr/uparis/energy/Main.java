package fr.uparis.energy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import fr.uparis.energy.utils.LevelConverter;
import fr.uparis.energy.view.MainMenuView;

public class Main {

    public static void main(String[] args) throws IOException {
        // return version
        if (args.length > 0 && args[0].replace("-", "").equalsIgnoreCase("version")) {
            try {
                InputStream is = Main.class.getClassLoader().getResourceAsStream("version.md");
                String version = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
                        .collect(Collectors.joining("\n")).strip();
                System.out.println(version);
                System.exit(0);
            } catch (Exception e) {
                System.out.println("Fail to get version in DesktopLauncher.");
            }
        }
        
        JFrame frame = new JFrame("Energy");

        frame.getContentPane().add(new MainMenuView(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        LevelConverter.copyBank1Levels();
    }
}
