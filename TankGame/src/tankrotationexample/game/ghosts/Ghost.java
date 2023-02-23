package tankrotationexample.game.ghosts;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources;
import tankrotationexample.game.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Ghost extends Moveable {

    private float vx;
    private float vy;
    private float angle;
    public float screenX, screenY;

    private static float R = 5; //speed
    private float ROTATIONSPEED = 3.0f;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private List<Animation> anims = new ArrayList<>(20);

    private int berry = 10;

    private Animation animation;
    private String animationType;
    private int id;

    public Ghost(float x, float y, int id, float angle, BufferedImage img){ //try adding List<BufferedImage> for anim
        super((int)x, (int)y, img);
        this.id = id;
        this.angle = angle;
        this.hitbox = new Rectangle((int)x,(int)y, this.img.getWidth(), this.img.getHeight());
    }

    private Animation characterAnimation() {
        if(this.id == 1) {
            return new Animation(this.x, this.y, Resources.getAnimation("green_ghost_idle"));
        }
        return new Animation(this.x, this.y, Resources.getAnimation("soulAnimation"));
    }

    public void setX(int x){ this.x = x; }

    public void setY(int y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update(GameWorld gw) {
        if (this.UpPressed) {
            this.moveUp();
        }

        if (this.DownPressed) {
            this.moveDown();
        }

        if (this.LeftPressed) {
            this.moveLeft();
        }

        if (this.RightPressed) {
            this.moveRight();
        }
        //this.animation.setX(this.x);
        //this.animation.setY(this.y);
        centerScreen();
    }
    //powerup example
    /*public void setCoolDown(long newCoolDown){
        this.coolDown = newCoolDown;
        if(this.coolDown < 500){
            this.coolDown = 500;
        }
    }
     */

    private int setBulletStartX(){
        float cx = 29f * (float) Math.cos(Math.toRadians(angle));
        return (int) x + this.img.getWidth() / 2 + (int) cx - 4;
    }

    private int setBulletStartY(){
        float cy = 29f * (float) Math.sin(Math.toRadians(angle));
        return (int) y + this.img.getHeight() / 2 + (int) cy - 4;
    }

    private void moveDown() {
        y += vx;
        checkBorder();
        centerScreen();
        this.hitbox.setLocation((int)x,(int)y);
    }

    private void moveUp() {
        y -= vx;
        checkBorder();
        centerScreen();
        this.hitbox.setLocation((int)x,(int)y);
    }

    private void moveRight() { //moveForwards
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        centerScreen();
        this.hitbox.setLocation((int)x,(int)y);
    }

    private void moveLeft() { //moveBackwards
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        centerScreen();
        this.hitbox.setLocation((int)x,(int)y);
    }
    public int getSouls(int score){
        return score;
    }

    public static void speedBoost(float speed) {
        R = speed;
    }

    public int berryBoost() {
        return berry;
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    private void centerScreen(){
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH/4f; //height if wanna do horizontal
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT/2f;

        if(this.screenX < 0) screenX = 0;
        if(this.screenY < 0) screenY = 0;

        if(this.screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2f){
            this.screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2f;
        }
        if(this.screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT){
            this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public int getScreenX(){
        return (int)screenX;
    }

    public int getScreenY(){
        return (int)screenY;
    }

    public void drawImage(Graphics g) {
        Graphics2D buffer = img.createGraphics();
        this.anims.forEach(a -> a.drawImage(buffer));

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        //this.animation.drawImage(g2d);
//------------------------------------
        //doAnim();

        //g2d.setColor(Color.RED);
        //g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }

    public void ghostPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox.setLocation((int) x, (int) y);
    }
}
