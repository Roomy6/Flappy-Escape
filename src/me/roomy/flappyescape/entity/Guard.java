package me.roomy.flappyescape.entity;

import me.roomy.flappyescape.GamePanel;
import me.roomy.flappyescape.handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Guard extends Entity
{
    GamePanel game;
    KeyHandler keyHandler;

    public BufferedImage guard;

    int minX = 5;
    int maxX = 180;

    public Guard(GamePanel game, KeyHandler keyHandler)
    {
        super(game, keyHandler);

        this.game = game;
        this.keyHandler = keyHandler;

        setupGuard();
        getImage();
    }

    public void setupGuard()
    {
        speed = 1;

        worldY = 110;
        worldX = 180;

        action = "left";
    }

    public void setAction()
    {
        actionCooldown++;

        if(actionCooldown == 100) {
            Random random = new Random();
            int i = random.nextInt(200) + 1;

            if(i < 50)
            {
                action = "left";
                //System.out.println(action);
            }

            if(i > 50)
            {
                action = "right";
                //System.out.println(action);
            }

            /* add */
            //action = "stand";

            actionCooldown = 0;
        }
    }

    public void getImage()
    {
        guard = game.textureManager.getTexture("guard");
    }

    public void update()
    {
        if(game.gameState == game.mainMenuState)
            setAction();
        else if(game.gameState == game.playState)
            action = "left";

        switch (action)
        {
            case "left":
                if (worldX >= minX)
                    worldX -= speed;

                if(game.gameState == game.playState)
                    worldX -= 4;
                break;

            case "right":
                if(worldX < maxX)
                    worldX += speed;
                break;

            case "stand":
                break;
        }
    }

    public void draw(Graphics2D g2)
    {
        g2.drawImage(guard, worldX, worldY, null);
    }
}
