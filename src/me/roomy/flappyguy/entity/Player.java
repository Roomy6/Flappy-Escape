package me.roomy.flappyguy.entity;

import me.roomy.flappyguy.GamePanel;
import me.roomy.flappyguy.handler.KeyHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
    GamePanel game;
    KeyHandler keyHandler;

    public BufferedImage player;

    private int targetX;
    private int targetY;

    private boolean transitioningToPlay = false;

    public Player(GamePanel game, KeyHandler keyHandler)
    {
        super(game, keyHandler);

        this.game = game;
        this.keyHandler = keyHandler;

        hitBox = new Rectangle();
        hitBox.width = 32;
        hitBox.height = 32;

        width = game.TILE_SIZE;
        height = game.TILE_SIZE;

        setupPlayer();

        getImage();
    }

    public void setupPlayer()
    {
        speed = 4;
        yVelocity = 0.0f;
        rotationAngle = 0.0f;
        rotationSpeed = 0.0f;

        worldY = 100;
        worldX = 280;

        isCollided = false;
        hasCollided = false;
        transitioningToPlay = false;
    }

    public void setYVelocity(int yDirection) {
        yVelocity = yDirection;
    }

    public void update()
    {
        if(game.gameState == game.mainMenuState)
        {
            yVelocity = 0;
        }

        if(game.gameState == game.playState)
        {
            if(!transitioningToPlay)
            {
                targetX = GamePanel.WINDOW_WIDTH / 4 - game.TILE_SIZE / 2;
                targetY = GamePanel.WINDOW_HEIGHT / 4 - game.TILE_SIZE / 2;

                transitioningToPlay = true;
            }

            moveToTarget();

            if(keyHandler.canFlap(isCollided))
            {
                setYVelocity(-speed);
                rotationSpeed = -5f + random.nextFloat() * 10f;
            }

            worldY += yVelocity;
            yVelocity += 0.3f;
            rotatePlayer();

            if (worldY < 0)
            {
                worldY = 0;
                setYVelocity(0);
                isCollided = true;
            }

            if (worldY + height > GamePanel.WINDOW_HEIGHT)
            {
                worldY = GamePanel.WINDOW_HEIGHT - height;
                setYVelocity(0);
                isCollided = true;
            }

            if(isCollided)
            {
                rotationSpeed = 0;
                keyHandler.canFlap(true);
                if(worldY == GamePanel.WINDOW_HEIGHT - height)
                    game.gameState = game.gameOverState;
            }
        }
    }

    private void moveToTarget()
    {
        float moveSpeed = 0.01f;

        worldX += (targetX - worldX) * moveSpeed;
        worldY += (targetY - worldY) * moveSpeed;

        if(Math.abs(targetX - worldX) < 1)
        {
            worldX = targetX;
        }

        if(Math.abs(targetY - worldY) < 1)
        {
            worldY = targetY;
        }
    }

    public void getImage()
    {
        player = game.loadImage("/player");
    }

    public void rotatePlayer()
    {
        rotationAngle += rotationSpeed;
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = player;
        AffineTransform old = g2.getTransform();

        int centerX = worldX + hitBox.width / 2;
        int centerY = worldY + hitBox.height / 2;

        // Rotate around player center
        g2.rotate(
                Math.toRadians(rotationAngle),
                worldX + width / 2.0,
                worldY + height / 2.0
        );

        g2.drawImage(image, worldX, worldY, width, height, null);

        // Reset transform
        g2.setTransform(old);

        if(keyHandler.debug) {
            g2.setColor(Color.RED);
            g2.drawRect(worldX + hitBox.x, worldY + hitBox.y, hitBox.width, hitBox.height);

            g2.setStroke(new BasicStroke(3));

            if (yVelocity < 0)
            {
                g2.setColor(Color.GREEN);

                g2.drawLine(centerX, centerY, centerX,centerY + ((int) yVelocity) * 6);
            }
            else if (yVelocity > 0)
            {
                g2.setColor(Color.RED);

                g2.drawLine(centerX, centerY, centerX,centerY + ((int) yVelocity) * 3);
            }
        }

        g2.setStroke(new BasicStroke(1));
    }
}
