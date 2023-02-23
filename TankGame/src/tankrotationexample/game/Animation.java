package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation extends Thread{
    float x,y;
    List<BufferedImage> frames;
    long delay = 85;
    long timeSinceLastFrameUpdate = 0;
    boolean isRunning = false;
    int currentFrame = 0;

    public Animation(float x, float y, List<BufferedImage> frames){
        this.frames = frames;
        this.x = x - this.frames.get(0).getWidth()/2f;
        this.y = y - this.frames.get(0).getHeight()/2f;

        this.timeSinceLastFrameUpdate = 0;
        this.isRunning = true;
    }

    public void setFrames(List<BufferedImage> frames) {
        this.frames = frames;
    }

    public void update(){
        if((this.timeSinceLastFrameUpdate + this.delay) < System.currentTimeMillis()){
            //currentFrame = (currentFrame + 1) % this.frames.size();
            currentFrame++;
            this.timeSinceLastFrameUpdate = System.currentTimeMillis();
            if(currentFrame > this.frames.size() -1){
                this.isRunning = false;
            }
        }
    }
    public void setX (float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void drawImage(Graphics2D g){
        if(isRunning){
            g.drawImage(this.frames.get(currentFrame), (int)x, (int)y, null);
        }
    }
}
