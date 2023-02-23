package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Stationary extends GameObject{

    public Stationary(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void drawImage(Graphics g) {}

}
