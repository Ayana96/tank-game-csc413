package tankrotationexample.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Soul extends Stationary{ //extends GameObject

    public Soul(float x, float y, BufferedImage img){
        super((int)x, (int)y, img);
    }
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.scale(2,2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation, null);
        g2d.setColor(Color.blue);
        g2d.drawRect((int)x,(int)y,this.img.getWidth()*2, this.img.getHeight()*2);
    }
    //checkBoarder from ghost?
}
