package tankrotationexample.game.powerups;

import tankrotationexample.game.Stationary;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends Stationary{

    PowerUp(float x, float y, BufferedImage img) {
        super((int)x, (int)y, img);
    }

    public void drawImage(Graphics g) {
        g.drawImage(super.img, (int) x, (int) y, null);
    }
}
