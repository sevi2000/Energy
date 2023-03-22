package fr.uparis.energy.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteBank {

    BufferedImage mainImage;

    private static int SQUARE_IMAGE_WIDTH = 120;
    private static int HEXAGON_IMAGE_HEIGHT = 120;

    public SpriteBank() throws IOException {
        mainImage = ImageIO.read(getClass().getClassLoader().getResource("images/tiles.png"));
    }

    public BufferedImage getNotPoweredLamp() {
        return mainImage.getSubimage(
                2 * SQUARE_IMAGE_WIDTH, 1 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH);
    }

    public BufferedImage getPoweredLamp() {
        return mainImage.getSubimage(
                3 * SQUARE_IMAGE_WIDTH, 1 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH);
    }
}
