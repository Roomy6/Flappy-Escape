package me.roomy.flappyguy.handler;

import me.roomy.flappyguy.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    GamePanel game;

    public boolean flapPressed, hasFlapped;
    public boolean debug, moveLeft, moveRight;

    public KeyHandler(GamePanel window) { this.game = window; }

    public boolean canFlap(boolean isCollided)
    {
        if(flapPressed && !hasFlapped && !game.player.hasCollided)
        {
            hasFlapped = true;
            return true;
        }
        else if(isCollided)
        {
            game.player.hasCollided = true;
            return false;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {  }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        if(game.gameState == game.playState) {
            if (code == KeyEvent.VK_SPACE) {
                flapPressed = true;
            }
        }

        if(game.gameState == game.mainMenuState)
        {
            if(code == KeyEvent.VK_SPACE)
            {
                game.gameState = game.playState;
            }
        }

        if(game.gameState == game.gameOverState)
        {
            if(code == KeyEvent.VK_SPACE)
            {
                game.gameState = game.mainMenuState;
                game.backgroundManager.setupBackground();
                game.player.setupPlayer();
                game.objectSetter.resetObjects();
                game.objectSetter.setObject();
            }
        }

        /* DEBUG */
        if(code == KeyEvent.VK_L)
        {
            debug = !debug;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            flapPressed = false;
            hasFlapped = false;
        }

        if(code == KeyEvent.VK_LEFT)
        {
            moveLeft = false;
        }

        if(code == KeyEvent.VK_RIGHT)
        {
            moveRight = false;
        }
    }
}
