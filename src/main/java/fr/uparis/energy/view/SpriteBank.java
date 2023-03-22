package fr.uparis.energy.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import fr.uparis.energy.model.Component;
import fr.uparis.energy.model.Geometry;


import javax.imageio.ImageIO;
import java.util.Map;

public class SpriteBank {

    BufferedImage mainImage;
    Map<Geometry, Map<State,BufferedImage>> bank;

    private static int SQUARE_IMAGE_WIDTH = 120;
    private static int HEXAGON_IMAGE_HEIGHT = 120;

    public SpriteBank() throws IOException {
        mainImage = ImageIO.read(getClass().getClassLoader().getResource("images/tiles.png"));
    }

    public  BufferedImage getSquareNotPoweredLamp() {
        return mainImage.getSubimage(2 * SQUARE_IMAGE_WIDTH, 1 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH);
    }

    public  BufferedImage getSquarePoweredLamp() {
        return mainImage.getSubimage(2 * SQUARE_IMAGE_WIDTH, 4 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH);
    }

    public  BufferedImage getHexagonNotPoweredLamp() {
        return mainImage.getSubimage(5 * SQUARE_IMAGE_WIDTH, 1 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH);
    }

    public  BufferedImage getHexagonPoweredLamp() {
        return mainImage.getSubimage(5 * SQUARE_IMAGE_WIDTH, 4 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH);
    }

    public BufferedImage getSquare() {
        return mainImage.getSubimage(0 * SQUARE_IMAGE_WIDTH,3 * SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH);
    }

    public BufferedImage getSprite(Geometry geometry, State state, Component cmp) {
        if (cmp == Component.LAMP) {
        if (geometry == Geometry.SQUARE) {
            if(state == State.POWERED) {
                return mainImage.getSubimage(2 * SQUARE_IMAGE_WIDTH, 4 * SQUARE_IMAGE_WIDTH, SQUARE_IMAGE_WIDTH,SQUARE_IMAGE_WIDTH);
            } else {
                return null;
            }
        } else {
            return null;
        }
    } else {
        return null;
    }
    }
}

