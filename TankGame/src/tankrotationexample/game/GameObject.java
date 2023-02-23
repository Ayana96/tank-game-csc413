package tankrotationexample.game;

import tankrotationexample.Resources;
import tankrotationexample.game.ghosts.Ghost;
import tankrotationexample.game.powerups.Berry;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected BufferedImage img;
    protected Rectangle hitbox;
    public boolean hasCollided;

    public GameObject(float x, float y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x, (int)y, img.getWidth(), img.getHeight());
        this.hasCollided = false;
    }

    public static GameObject gameObjectFactory(String type, float x, float y){
         return switch(type){
             case "7" ->    new Berry(x,y, Resources.getSprite("berry"));
             case "9" ->    new Wall(x,y, Resources.getSprite("unbreak"));
             case "8" ->    new Soul(x, y, Resources.getSprite("soul")) {
             };
             //magnetism
             //speed-boost
             //berry

             //case "2" -> {
             //walls.add(new GreenBase(i*30,j*30, Resources.getSprite("break")));
             //}
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
    public Rectangle getHitBox(){
        return this.hitbox.getBounds();
    }

    public abstract void drawImage(Graphics g);

    public int getX(){
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public void ghostPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox.setLocation((int) x, (int) y);
    }

    public void handleCollision(GameObject ob2) {
        if(ob2 instanceof Wall){
            Rectangle r1 = this.getHitBox().intersection(ob2.getHitBox());
            if(r1.height > r1.width && this.x < r1.x){
                ghostPosition(this.x -= r1.width/2f, this.y);
            } else if(r1.height > r1.width && this.x > (ob2).getX()){
                ghostPosition(this.x += r1.width/2f, this.y);
            } else if(r1.height < r1.width && this.y < r1.y){
                this.y -= r1.height/2f;
            } else if(r1.height < r1.width && this.y > (ob2).getY()){
                this.y += r1.height/2f;
            }
            //System.out.println("hit wall");
        }
    }
}
