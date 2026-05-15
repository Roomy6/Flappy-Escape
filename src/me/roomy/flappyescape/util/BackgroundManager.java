package me.roomy.flappyescape.util;

import me.roomy.flappyescape.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundManager
{
    GamePanel game;

    private BufferedImage backgroundImage, groundImage, prisonImage;
    private float bg1x, bg2x, groundX, prisonX;
    private float bgSpeed, frgSpeed;

    public BackgroundManager(GamePanel game)
    {
        this.game = game;

        backgroundImage = game.textureManager.getTexture("background");
        groundImage = game.textureManager.getTexture("ground");
        prisonImage = game.textureManager.getTexture("prison");

        setupBackground();
    }

    public void setupBackground()
    {
        bg1x = 0;
        bg2x = backgroundImage.getWidth();

        groundX = 0;
        prisonX = 0;

        bgSpeed = 0.5f;

        frgSpeed = 4;
    }

    public void update()
    {
        if(game.gameState == game.playState) {
            if (!game.player.hasCollided) {
                bg1x -= bgSpeed;
                bg2x -= bgSpeed;

                groundX -= frgSpeed;
                prisonX -= frgSpeed;
            }
        }

        /* bg 1 */
        if (bg1x + backgroundImage.getWidth() <= 0)
        {
            bg1x = bg2x + backgroundImage.getWidth();
        }

        /* bg 2 */
        if (bg2x + backgroundImage.getWidth() <= 0)
        {
            bg2x = bg1x + backgroundImage.getWidth();
        }
    }

    public void draw(Graphics2D g2)
    {
        g2.drawImage(backgroundImage, (int)bg1x, 0, null);
        g2.drawImage(backgroundImage, (int)bg2x, 0, null);

        for (int x = (int)groundX; x < GamePanel.WINDOW_WIDTH; x += groundImage.getWidth())
        {
            g2.drawImage(groundImage, x, GamePanel.WINDOW_HEIGHT - groundImage.getHeight(), null);
        }

        if(prisonX < prisonImage.getWidth())
        {
            g2.drawImage(prisonImage, (int)prisonX, GamePanel.WINDOW_HEIGHT - prisonImage.getHeight(), null);
        }
    }
}
