package me.roomy.flappyescape.util;

import me.roomy.flappyescape.GamePanel;
import me.roomy.flappyescape.object.PipeObject;

import java.util.Random;

public class ObjectSetter
{
    GamePanel game;
    Random random = new Random();

    int x;
    int gapSize;
    int topPadding;
    int bottomPadding;

    public ObjectSetter(GamePanel game)
    {
        this.game = game;
    }

    public void setObject()
    {
        resetObjects();

        /* Have a +2 for i due to adding a bottom pipe */
        for(int i = 0; i < game.objects.length; i += 2) {
            PipeObject pipe = new PipeObject(game);

            int minGapY = topPadding;
            int maxGapY = GamePanel.WINDOW_HEIGHT - gapSize - bottomPadding;

            if(maxGapY <= minGapY)
            {
                maxGapY = minGapY + game.TILE_SIZE * 3;
            }

            int gapY = random.nextInt(maxGapY - minGapY) + minGapY;
            gapY = (gapY / game.TILE_SIZE) * game.TILE_SIZE;

            pipe.worldX = x;
            pipe.worldY = gapY - game.TILE_SIZE;

            game.objects[i] = pipe;
            game.objects[i + 1] = pipe;

            /* Padding between pipes */
            x += game.TILE_SIZE * 8;
        }
    }

    public void resetObjects()
    {
        for (int i = 0; i < game.objects.length; i++)
        {
            game.objects[i] = null;
        }

        x = GamePanel.WINDOW_WIDTH * 2;

        gapSize = game.TILE_SIZE * 5;
        topPadding = game.TILE_SIZE * 3;
        bottomPadding = GamePanel.WINDOW_HEIGHT - game.TILE_SIZE * 3;
    }
}
