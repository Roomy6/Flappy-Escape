package me.roomy.flappyescape.util;

import me.roomy.flappyescape.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TextureManager
{
    GamePanel game;

    private final Map<String, BufferedImage> textures = new HashMap<>();

    public TextureManager(GamePanel game)
    {
        this.game = game;
        loadTextures();
    }

    private void loadTextures()
    {
        loadTexture("Player", "/player.png");
    }

    /* Load texture and store in memory */
    public void loadTexture(String key, String path)
    {
        try
        {
            InputStream stream = getClass().getResourceAsStream(path);
            BufferedImage image;

            if(stream == null)
            {
                System.out.println("[!] Missing texture: " + path);
                textures.put(key, createMissingTexture(game.TILE_SIZE, game.TILE_SIZE));

                return;
            }

            image = ImageIO.read(stream);
            textures.put(key, image);

            System.out.println("[+] Loaded Texture: " + key);

        } catch (Exception e)
        {
            e.printStackTrace();

            textures.put(key, createMissingTexture(game.TILE_SIZE, game.TILE_SIZE));
        }
    }

    public BufferedImage getTexture(String key)
    {
        return textures.getOrDefault(key, createMissingTexture(game.TILE_SIZE, game.TILE_SIZE));
    }

    private BufferedImage createMissingTexture(int width, int height)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();

        int tile = 8;

        for(int y = 0; y < height; y += tile)
        {
            for(int x = 0; x < width; x += tile)
            {
                boolean pink = ((x / tile) + (y / tile)) % 2 == 0;

                g.setColor(pink? Color.MAGENTA : Color.BLACK);
                g.fillRect(x, y, tile, tile);
            }
        }

        g.dispose();
        return  image;
    }

}
