package me.roomy.flappyescape.util;

import me.roomy.flappyescape.GamePanel;
import me.roomy.flappyescape.object.PipeObject;

import java.awt.*;

public class CollisionManager
{
    GamePanel game;

    public CollisionManager(GamePanel game)
    {
        this.game = game;
    }

    public void update()
    {
        if (game.player == null) return;

        Rectangle playerBox = new Rectangle(
                game.player.worldX + game.player.hitBox.x,
                game.player.worldY + game.player.hitBox.y,
                game.player.hitBox.width,
                game.player.hitBox.height
        );

        for (int i = 0; i < game.objects.length; i++)
        {
            if (game.objects[i] == null) continue;

            PipeObject pipe = (PipeObject) game.objects[i];

            int x = pipe.worldX + pipe.boundingBox.x;
            int width = pipe.boundingBox.width;

            // TOP PIPE
            Rectangle topPipeBox = new Rectangle(
                    x,
                    0,
                    width,
                    pipe.worldY + pipe.boundingBox.height
            );

            // BOTTOM PIPE
            int bottomStartY = pipe.worldY + pipe.gapSize;

            Rectangle bottomPipeBox = new Rectangle(
                    x,
                    bottomStartY,
                    width,
                    GamePanel.WINDOW_HEIGHT - bottomStartY
            );

            if (playerBox.intersects(topPipeBox) ||
                    playerBox.intersects(bottomPipeBox))
            {
                game.player.isCollided = true;
                return;
            }
        }

        game.player.isCollided = false;
    }
}
