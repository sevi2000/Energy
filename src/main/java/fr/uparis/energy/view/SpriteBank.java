package fr.uparis.energy.view;

import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
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
        URL resource = SpriteBank.class.getClassLoader().getResource("images/tiles.png");
        if (resource == null) throw new IllegalStateException();
        try {
            SpriteBank.mainImage = ImageIO.read(resource);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    private static BufferedImage getImageAt(int i, int j) {
        int height = j >= 3 ? HEXAGON_IMAGE_HEIGHT : SQUARE_IMAGE_HEIGHT;
        return mainImage.getSubimage(
                SQUARE_IMAGE_WIDTH * j, SQUARE_IMAGE_HEIGHT * i,
                SQUARE_IMAGE_WIDTH, height
        );
    }

    public static BufferedImage getComponent(Geometry geometry, PowerState powerState, Component cmp) {
        if (cmp == Component.EMPTY)
            return switch (geometry) {
                case SQUARE -> getImageAt(1, 0);
                case HEXAGON -> getImageAt(1, 3);
            };

        if (cmp == Component.SOURCE && powerState == PowerState.NOT_POWERED)
            throw new IllegalArgumentException("Not powered source");

        int i = 1;

        int j = switch (cmp) {
            case SOURCE -> 0;
            case WIFI -> 1;
            case LAMP -> 2;
            case EMPTY -> throw new IllegalStateException();
        };

        if (powerState == PowerState.POWERED) i += 3;
        if (geometry == Geometry.HEXAGON) j += 3;

        return getImageAt(i, j);
    }

    public static BufferedImage getSquare(PowerState powerState) {
        return switch (powerState) {
            case NOT_POWERED -> getImageAt(0, 0);
            case POWERED -> getImageAt(3, 0);
        };
    }

    public static BufferedImage getHexagon(PowerState powerState) {
        return switch (powerState) {
            case NOT_POWERED -> getImageAt(0, 3);
            case POWERED -> getImageAt(3, 3);
        };
    }
}
