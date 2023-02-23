package tankrotationexample.game.powerups;

import tankrotationexample.game.ghosts.Ghost;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Berry extends PowerUp {

    public Berry(float x, float y, BufferedImage img) {
        super(x,y,img);
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.scale(2,2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation, null);
        g2d.setColor(Color.PINK);
        g2d.drawRect((int)x,(int)y,this.img.getWidth()*2, this.img.getHeight()*2);
    }
}
