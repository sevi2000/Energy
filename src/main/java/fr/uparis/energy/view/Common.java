package fr.uparis.energy.view;

import fr.uparis.energy.model.Level;
import fr.uparis.energy.utils.IntPair;
import fr.uparis.energy.utils.LevelConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Common {
    private Common() {
    }

    public static JLabel createButton(String title, MouseAdapter adapter) {
        JLabel res = new JLabel(title);
        res.setOpaque(true);
        res.setForeground(Color.BLACK);
        res.setBackground(Color.GRAY);

        Font font = new Font("Arial", Font.BOLD, 40);
        res.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        res.setFont(font);

        if (adapter != null) {
            res.addMouseListener(adapter);
            res.addMouseMotionListener(adapter);
        }
        res.setHorizontalAlignment(SwingConstants.CENTER);
        return res;
    }

    public static JLabel createButton(String title, int border, MouseAdapter adapter) {
        JLabel button = createButton(title, adapter);
        button.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        return button;
    }


    public static JPanel centeredPane(List<Component> children, int width, int col, int height) {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(children.size(), col));
        for (Component component : children) content.add(component);
        content.setBorder(BorderFactory.createEmptyBorder(height, width, width, height));
        return content;
    }

    public static void drawRotatedImage(Graphics g, int x, int y, int width, int height, int angle, Image img) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform af = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x + width / 2.0, y + height / 2.0);
        g2d.drawImage(img, x, y, width, height, null);
        g2d.setTransform(af);
    }

    public static IntPair getClosestPolygon(BoardView bv, int x, int y) {
        double minDistance = Double.POSITIVE_INFINITY;
        IntPair clickedPolygon = new IntPair(0, 0);
        for (Map.Entry<IntPair, IntPair> entry : bv.getCoordinateMap().entrySet()) {
            double distance = Math.sqrt(Math.pow(entry.getKey().a - x, 2) +
                    Math.pow(entry.getKey().b - y, 2));

            if (distance < minDistance) {
                minDistance = distance;
                clickedPolygon = entry.getValue();
            }
        }
        return clickedPolygon;
    }

    public static <V, K> Map<V, K> invertMapUsingStreams(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public static void showConfirmation(String title, String msg, JFrame parentWindow, Level lvl) {

        int result = JOptionPane.showConfirmDialog(parentWindow, msg, title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION && lvl.isSolved()) {
            try {
                LevelConverter.writeLevelToFile(lvl);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } /*else if (result == JOptionPane.NO_OPTION){
            return;
        }*/
        parentWindow.setContentPane(new MainMenuView(parentWindow));
        parentWindow.setVisible(true);
    }
}