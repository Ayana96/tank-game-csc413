package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Moveable extends GameObject {

    public Moveable(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void drawImage(Graphics g) {

    }
}
