package fr.uparis.energy.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Common {
    private Common() {}

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

    public static JPanel centeredPane(List<Component> children, int border, int col) {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(children.size(), col));
        for (Component component : children) content.add(component);
        content.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        return content;
    }
    
    public static void drawRotatedImage(Graphics g, int x, int y, int width, int height, int angle, Image img){
        Graphics2D g2d = (Graphics2D)g; 
        AffineTransform af = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle),x + width / 2,y + height/2);
        g2d.drawImage(img,x,y,width,height,null);
        g2d.setTransform(af);

    }
}
