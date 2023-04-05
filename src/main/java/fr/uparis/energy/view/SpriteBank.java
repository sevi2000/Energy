package fr.uparis.energy.view;

import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteBank {
    // TODO Get wire images

    private static BufferedImage mainImage;

    private static final int SQUARE_IMAGE_WIDTH = 120;
    private static final int SQUARE_IMAGE_HEIGHT = 120;
    private static final int HEXAGON_IMAGE_WIDTH = 120;
    private static final int HEXAGON_IMAGE_HEIGHT = 104;

    private SpriteBank() {}

    static {
        try {
            SpriteBank.mainImage =
                    ImageIO.read(SpriteBank.class.getClassLoader().getResource("images/tiles.png"));
        } catch (IOException e) {
        }
    }

    public static BufferedImage getComponent(Geometry geometry, PowerState powerState, Component cmp) {
        if (geometry == Geometry.SQUARE) {
            if (powerState == PowerState.POWERED) {
                return switch (cmp) {
                    case LAMP -> SpriteBank.mainImage.getSubimage(
                            2 * SQUARE_IMAGE_WIDTH, 4 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                    case EMPTY -> SpriteBank.mainImage.getSubimage(
                            1 * SQUARE_IMAGE_WIDTH, 0 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                    case SOURCE -> SpriteBank.mainImage.getSubimage(
                            0 * SQUARE_IMAGE_WIDTH, 4 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                    case WIFI -> SpriteBank.mainImage.getSubimage(
                            1 * SQUARE_IMAGE_WIDTH, 4 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                };
            } else {
                return switch (cmp) {
                    case LAMP -> SpriteBank.mainImage.getSubimage(
                            1 * SQUARE_IMAGE_WIDTH, 1 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                    case EMPTY -> SpriteBank.mainImage.getSubimage(
                            1 * SQUARE_IMAGE_WIDTH, 0 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                    case SOURCE -> throw new IllegalArgumentException();
                    case WIFI -> SpriteBank.mainImage.getSubimage(
                            1 * SQUARE_IMAGE_WIDTH, 1 * SQUARE_IMAGE_HEIGHT, SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_HEIGHT);
                };
            }
        } else { // else HEXAGON
            if (powerState == PowerState.POWERED) {
                return switch (cmp) {
                    case LAMP -> SpriteBank.mainImage.getSubimage(
                            5 * HEXAGON_IMAGE_WIDTH,
                            4 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                    case EMPTY -> SpriteBank.mainImage.getSubimage(
                            1 * HEXAGON_IMAGE_WIDTH,
                            0 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                    case SOURCE -> SpriteBank.mainImage.getSubimage(
                            3 * HEXAGON_IMAGE_WIDTH,
                            4 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                    case WIFI -> SpriteBank.mainImage.getSubimage(
                            4 * HEXAGON_IMAGE_WIDTH,
                            4 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                };
            } else {
                return switch (cmp) {
                    case LAMP -> SpriteBank.mainImage.getSubimage(
                            5 * HEXAGON_IMAGE_WIDTH,
                            1 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                    case EMPTY -> SpriteBank.mainImage.getSubimage(
                            1 * HEXAGON_IMAGE_WIDTH,
                            0 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                    case SOURCE -> throw new IllegalArgumentException();
                    case WIFI -> SpriteBank.mainImage.getSubimage(
                            4 * HEXAGON_IMAGE_WIDTH,
                            1 * HEXAGON_IMAGE_HEIGHT,
                            HEXAGON_IMAGE_WIDTH,
                            HEXAGON_IMAGE_HEIGHT);
                };
            }
        }
    }
}
