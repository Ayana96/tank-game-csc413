package tankrotationexample;

import tankrotationexample.game.GameWorld;
import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Resources {

    private static Map<String, BufferedImage> sprites = new HashMap<>();
    private static Map<String, Sound> sounds = new HashMap<>();
    private static Map<String, List<BufferedImage>> animations = new HashMap<>();


    private static BufferedImage loadSprite(String path) throws IOException{
        return ImageIO.read(Objects.
                requireNonNull(GameWorld
                    .class
                    .getClassLoader()
                    .getResource(path),
                    "Could not find " + path));
    }


    private static void initSprites() {
        try {
            Resources.sprites.put("menu", loadSprite("menu/main.png"));

            Resources.sprites.put("ghost1", loadSprite("ghost/ghost.png"));
            Resources.sprites.put("ghost2", loadSprite("ghost/ghost2.png"));

            Resources.sprites.put("greenGhost1up", loadSprite("animations/greenGhost/green_ghost_left_0000.png"));

            Resources.sprites.put("floor", loadSprite("floor/background3.jpg"));

            Resources.sprites.put("unbreak", loadSprite("walls/unbreak.jpg"));

            Resources.sprites.put("soul", loadSprite("soul/soul.png"));

            Resources.sprites.put("berry", loadSprite("powerups/berry.png"));

            //Resources.sprites.put("soul",
                    //ImageIO.read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("soul/soul.png"))));

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static Sound loadSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream as = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(Resources.class.getClassLoader().getResource(path)));
        Clip clip;
        clip = AudioSystem.getClip();
        clip.open(as);
        Sound s = new Sound(clip);
        s.setVolume(0.1f);
        return s;
    }

    private static  void initSounds() {
        try{
            Resources.sounds.put("music", loadSound("sounds/outside.wav"));
            Resources.sounds.put("souls", loadSound("sounds/soul_sound.wav"));
            Resources.sounds.put("hurry", loadSound("sounds/hurry_up.wav"));
            Resources.sounds.put("berry", loadSound("sounds/berry_boost.wav"));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static void initAnimations() {
        try{
            String base = "animations/greenGhost/idle/green_ghost_%04d.png"; //1-0001
            List<BufferedImage> temp = new ArrayList<>();
            for(int i = 0; i < 11; i++){
                String fName = base.format(base, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("green_ghost_idle",temp);

            base = "animations/greenGhost/green_ghost_left_%04d.png";
            temp = new ArrayList<>();
            for(int i = 0; i < 2; i++){
                String fName = base.format(base, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("green_ghost_left", temp);

            base = "animations/greenGhost/green_ghost_right_%04d.png";
            temp = new ArrayList<>();
            for(int i = 0; i < 2; i++){
                String fName = base.format(base, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("green_ghost_right", temp);

            base = "soul/soul_%04d.png";
            temp = new ArrayList<>();
            for(int i = 0; i < 8; i++){
                String fName = base.format(base, i);
                temp.add(loadSprite(fName));
            }
            Resources.animations.put("soulAnimation", temp);


            System.out.println();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e);
            System.exit(-3);
        }
    }

    public static void loadResources() {
        initSprites();;
        initSounds();
        initAnimations();
    }

    public static BufferedImage getSprite(String key) {
        if(!Resources.sprites.containsKey(key)){
            System.out.println(key + " resource not found");
            System.exit(-2);
        }
        return Resources.sprites.get(key);
    }

    public static Sound getSound(String key) {
        if(!Resources.sounds.containsKey(key)){
            System.out.println(key + " sound not found");
            System.exit(-2);
        }
        return Resources.sounds.get(key);
    }

    public static List<BufferedImage> getAnimation(String key) {
        if(!Resources.animations.containsKey(key)){
            System.out.println(key + " animation not found");
            System.exit(-2);
        }
        return Resources.animations.get(key);
    }
}
