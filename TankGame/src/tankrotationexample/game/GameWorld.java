/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources;
import tankrotationexample.game.ghosts.Ghost;
import tankrotationexample.game.ghosts.GhostControl;
import tankrotationexample.game.powerups.Berry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Sound bgMusic;

    private Ghost t1;
    private Ghost t2;
    private Launcher lf;
    private long tick = 0;
    private List<GameObject> gameObjects = new ArrayList<>();

    private List<Animation> anims = new ArrayList<>(20);

    protected float x;
    protected float y;
    int score;
    private float R = 2;


    /**
     * @param lf
     */
    public GameWorld(Launcher lf) { this.lf = lf; }

    @Override
    public void run() {
        try {
            this.resetGame();
            bgMusic = Resources.getSound("music");
            bgMusic.setVolume(0.2f);
            bgMusic.setLooping();
            bgMusic.playSound();
            while (true) {
                this.tick++;
                this.t1.update(this); // update tank
                this.t2.update(this); // update tank
                this.checkCollisions();
                this.anims.forEach(Animation::update); //this.anims.forEach(a -> a.update());
                this.gameObjects.removeIf(g -> g.hasCollided);
                //this.anims.removeIf(a -> !a.isRunning);
                this.repaint();   // redraw game

                Thread.sleep(1000 / 144);

                /*if(this.t1.getIsLoser()) {
                    System.out.println("ghost 1 wins");
                    bgMusic.stopSound();
                    this.lf.setFrame("end");
                    return;
                }else if(this.t2.getIsLoser()) {
                    System.out.println("ghost2 wins");
                    bgMusic.stopSound();
                    this.lf.setFrame("end");
                    return;
                }
                 */

                if (this.tick >= 144 * 52) { //--8 = 16 seconds //64 - 2mins //42
                    this.lf.setFrame("end");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }


    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(100);
        this.t1.setY(725);
        this.t2.setX(1750);
        this.t2.setY(725);
    }

    private void checkCollisions() {
        for (int i = 0; i < this.gameObjects.size(); i++) {
            for (int j = 0; j < this.gameObjects.size(); j++) {
                GameObject ob1 = this.gameObjects.get(i);
                GameObject ob2 = this.gameObjects.get(j);
                if (i == j) continue;
                if (ob1.getHitBox().intersects(ob2.getHitBox())) { //(ob1.getHitBox().intersects(ob1.getHitBox()) --magnetism??
                    ob1.handleCollision(ob2);
                    if(ob2 instanceof Soul){
                        ob2.hasCollided = true;
                        score++;
                        Resources.getSound("souls").playSound();
                        //if(score == 50){
                        //Ghost.speedBoost(R + 8);
                        //System.out.println("speedBoost");
                        //this.anims.add(new Animation(t1.x,t1.y, Resources.getAnimation("green_ghost_idle")));
                        //"green_ghost_left"
                        //}
                    }
                    if(ob2 instanceof Berry){
                        ob2.hasCollided = true;
                        Ghost.speedBoost(R + 8);
                        Resources.getSound("berry").playSound();
                    }
                }
            }
        }
    }

    /**
     * Load all resources for Ghost Game. Set all Game Objects to their
     * initial state as well.
     */

    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("maps/map2.csv")))) {
            for(int i = 0; mapReader.ready(); i++){
                String[] gameObjects = mapReader.readLine().split(",");
                for(int j = 0; j < gameObjects.length; j++){
                    String objectType = gameObjects[j];
                    if(Objects.equals("0",objectType)) continue;
                    this.gameObjects.add(GameObject.gameObjectFactory(objectType,j*30,i*30));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

                         //300 by 300
        t1 = new Ghost(100, 725, 1, 0, Resources.getSprite("ghost1"));
        GhostControl tc1 = new GhostControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Ghost(1750, 725, 2, 0, Resources.getSprite("ghost2"));
        GhostControl tc2 = new GhostControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);

        this.anims.add(new Animation(t1.x,t1.y, Resources.getAnimation("green_ghost_idle")));
        this.gameObjects.add(t1);
        this.gameObjects.add(t2);
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        drawFloor(buffer);

        this.gameObjects.forEach(gObj -> gObj.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        this.anims.forEach(a -> a.drawImage(buffer));

        drawSplitScreens(g2, world);
        drawMiniMap(g2, world);

        //how many souls collected
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        //g2.setColor(Color.YELLOW);
        //g2.drawString("Time: " + counterLabel , GameConstants.GAME_SCREEN_WIDTH * 50/80, GameConstants.GAME_SCREEN_HEIGHT * 5/40);
        g2.setColor(Color.WHITE);  //this.t1.getSouls()
        g2.drawString("Spirits : " + this.t1.getSouls(score), GameConstants.GAME_SCREEN_WIDTH * 50/80, GameConstants.GAME_SCREEN_HEIGHT * 35/40);

        g2.setColor(Color.WHITE);  //;this.t2.getSouls()
        g2.drawString("Spirits : " + this.t2.getSouls(score), GameConstants.GAME_SCREEN_WIDTH * 3/80, GameConstants.GAME_SCREEN_HEIGHT * 35/40);
    }

    void drawFloor(Graphics2D buffer){
        for (int i = 0; i < GameConstants.WORLD_WIDTH; i += 1920){ //320
            for(int j = 0; j < GameConstants.WORLD_HEIGHT; j += 1560){ //240
                buffer.drawImage(Resources.getSprite("floor"), i, j, null);
            }
        }
    }

    void drawMiniMap(Graphics2D g, BufferedImage world){
        BufferedImage mm = world.getSubimage(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.WORLD_WIDTH*.2f)/2f,
                        GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.WORLD_HEIGHT*.2f));
        at.scale(.17,.17);
        g.drawImage(mm,at,null);
    }

    void drawSplitScreens(Graphics2D g, BufferedImage world){
        BufferedImage lh = world.getSubimage(t1.getScreenX(),
                t1.getScreenY(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);

        BufferedImage rh = world.getSubimage(t2.getScreenX(),
                t2.getScreenY(),
                GameConstants.GAME_SCREEN_WIDTH/2,
                GameConstants.GAME_SCREEN_HEIGHT);
        g.drawImage(lh,0,0, null);
        g.drawImage(rh,GameConstants.GAME_SCREEN_WIDTH/2,0, null);
    }
}
