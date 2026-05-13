package me.roomy.flappyguy.util;

import me.roomy.flappyguy.GamePanel;
import me.roomy.flappyguy.object.PipeObject;

import java.util.Random;

public class ObjectSetter
{
    GamePanel game;
    Random random = new Random();

    public ObjectSetter(GamePanel game)
    {
        this.game = game;
    }

    public void setObject()
    {
        int x = GamePanel.WINDOW_WIDTH;

        int gapSize = game.TILE_SIZE * 5;
        int topPadding = game.TILE_SIZE * 3;
        int bottomPadding = GamePanel.WINDOW_HEIGHT - game.TILE_SIZE * 3;

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
}
