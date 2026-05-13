package me.roomy.flappyguy.object;

import me.roomy.flappyguy.GamePanel;
import me.roomy.flappyguy.util.ScaleManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject extends Rectangle
{
    public BufferedImage image;
    public String name;
    public boolean enableCollision = false;
    public int worldX, worldY;

    /* I don't know how else to do this */
    public int gapSize = 5 * 32;

    public Rectangle boundingBox = new Rectangle(0, 0, 32, 32);
    ScaleManager scaleManager = new ScaleManager();

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