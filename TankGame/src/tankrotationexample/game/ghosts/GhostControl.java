/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game.ghosts;


import tankrotationexample.game.ghosts.Ghost;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author anthony-pc
 */
public class GhostControl implements KeyListener {

    private Ghost g1;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot; //collect

    
    public GhostControl(Ghost g1, int up, int down, int left, int right, int shoot) {
        this.g1 = g1;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.g1.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.g1.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.g1.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.g1.toggleRightPressed();
        }
        if(keyPressed == shoot) {
            this.g1.toggleShootPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.g1.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.g1.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.g1.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.g1.unToggleRightPressed();
        }
        if (keyReleased == shoot){

        }
    }
}
