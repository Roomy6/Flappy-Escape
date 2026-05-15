package me.roomy.flappyescape.entity;

import me.roomy.flappyescape.GamePanel;
import me.roomy.flappyescape.handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Entity
{
    GamePanel game;
    KeyHandler keyHandler;

    public int width, height;

    public int worldX, worldY;
    public int speed;
    public float yVelocity, xVelocity;
    public float rotationAngle, rotationSpeed;

    public String action;
    public int actionCooldown = 0;

    public Rectangle hitBox = new Rectangle(0, 0, 32, 32);
    public boolean isCollided, hasCollided;

    public Random random = new Random();

    public Entity(GamePanel game, KeyHandler keyHandler)
    {
        this.game = game;
        this.keyHandler = keyHandler;
    }

    public void update()
    {
        isCollided = false;
    }

    public void draw(Graphics2D g2)
    {
        if(keyHandler.debug)
        {
            g2.setColor(Color.RED);
            g2.drawRect(worldX + hitBox.x, worldY + hitBox.y, hitBox.width, hitBox.height);
        }
    }
}
