package me.roomy.flappyescape.object;

import me.roomy.flappyescape.GamePanel;
import me.roomy.flappyescape.util.ScaleManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject extends Rectangle
{
    GamePanel game;

    public BufferedImage image;
    public boolean enableCollision = false;
    public int worldX, worldY;

    /* I don't know how else to do this */
    public int gapSize = 5 * 32;

    public Rectangle boundingBox = new Rectangle(0, 0, 0, 0);
    ScaleManager scaleManager = new ScaleManager();

    public SuperObject(GamePanel game)
    {
        this.game = game;

        boundingBox.width = game.TILE_SIZE;
        boundingBox.height = game.TILE_SIZE;
    }

    public void draw(Graphics2D g2, GamePanel game)
    {
        //if(worldX > game.TILE_SIZE && worldX < GamePanel.WINDOW_WIDTH - game.TILE_SIZE)
        if(worldX > -game.TILE_SIZE && worldX < GamePanel.WINDOW_WIDTH)
        {
            g2.drawImage(image, worldX, worldY, game.TILE_SIZE, game.TILE_SIZE, null);
        }
        else {
            if(worldX + game.TILE_SIZE > GamePanel.WINDOW_WIDTH)
            {
                /* remove object */
            }
        }
    }
}