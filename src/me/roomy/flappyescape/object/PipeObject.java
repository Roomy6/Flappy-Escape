package me.roomy.flappyescape.object;

import me.roomy.flappyescape.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PipeObject extends SuperObject
{
    GamePanel game;
    BufferedImage topPipe, bottomPipe, basePipe;

    public PipeObject(GamePanel game)
    {
        super(game);

        topPipe = game.textureManager.getTexture("pipeTop");
        bottomPipe = game.textureManager.getTexture("pipeTop");
        basePipe = game.textureManager.getTexture("pipeBase");

        topPipe = flipVertical(topPipe);

        topPipe = scaleManager.scaledImage(topPipe, game.TILE_SIZE, game.TILE_SIZE);
        bottomPipe = scaleManager.scaledImage(bottomPipe, game.TILE_SIZE, game.TILE_SIZE);
        basePipe = scaleManager.scaledImage(basePipe, game.TILE_SIZE, game.TILE_SIZE);
    }

    public BufferedImage flipVertical(BufferedImage image)
    {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight());

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(image, null);
    }

    @Override
    public void draw(Graphics2D g2, GamePanel game)
    {
        //if(worldX > game.TILE_SIZE && worldX < GamePanel.WINDOW_WIDTH - game.TILE_SIZE)
        if(worldX > -game.TILE_SIZE && worldX < GamePanel.WINDOW_WIDTH)
        {
            /* Top Pipe */
            for (int y = worldY; y >= 0; y -= game.TILE_SIZE) {
                if (y == worldY) {
                    g2.drawImage(topPipe, worldX, y, game.TILE_SIZE, game.TILE_SIZE, null);
                } else
                    g2.drawImage(basePipe, worldX, y, game.TILE_SIZE, game.TILE_SIZE, null);
            }

            /* Bottom Pipe */
            int bottomStart = worldY + gapSize;

            for (int y = bottomStart; y < GamePanel.WINDOW_HEIGHT; y += game.TILE_SIZE)
            {
                if(y == bottomStart)
                    g2.drawImage(bottomPipe, worldX, y, game.TILE_SIZE, game.TILE_SIZE, null);
                else
                    g2.drawImage(basePipe, worldX, y, game.TILE_SIZE, game.TILE_SIZE, null);
            }
        }
    }
}
