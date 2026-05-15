package me.roomy.flappyescape.ui;

import javax.imageio.ImageIO;

import me.roomy.flappyescape.util.FontUtil;
import me.roomy.flappyescape.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite
{
    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 8;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;

    public Sprite(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("[*] Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;

        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("[*] Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;

        loadSpriteArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wSprite = SPRITESHEET.getWidth() / w;
    }

    public void setHeight(int i) {
        h = i;
        hSprite = SPRITESHEET.getHeight() / h;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("[!] Error: Could not load file: " + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[wSprite][hSprite];

        for (int x = 0; x < wSprite; x++) {
            for (int y = 0; y < hSprite; y++) {
                spriteArray[x][y] = getSprite(x, y);
            }
        }
    }

    public BufferedImage getSpriteSheet() { return SPRITESHEET; }

    public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public BufferedImage[][] getSpriteArray2(int i) {
        return spriteArray;
    }

    public static void drawString(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for(int i = 0; i < img.size(); i++) {
            if(img.get(i) != null) {
                g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawString(Graphics2D g, FontUtil f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != 32) {
                g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawStringWithShadow(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        // Loop through the images and draw the shadow first
        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                BufferedImage shadowImage = createShadowImage(img.get(i));
                g.drawImage(shadowImage, (int) (x - 3), (int) (y - 3), width, height, null); // Draw shadow with offset
            }
            x += xOffset;
            y += yOffset;
        }

        // Draw the actual string (original color)
        x = pos.x;
        y = pos.y;
        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawStringWithShadow(Graphics2D g, FontUtil f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        // Loop through each character in the string and draw the shadow first
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32) { // Check for spaces
                BufferedImage shadowImage = createShadowImage(f.getFont(word.charAt(i)));
                g.drawImage(shadowImage, (int) (x + 3), (int) (y + 3), width, height, null); // Draw shadow with offset
            }

            x += xOffset;
            y += yOffset;
        }

        // Draw the actual string (original color)
        x = pos.x;
        y = pos.y;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32) { // Check for spaces
                g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

    private static BufferedImage createShadowImage(BufferedImage original) {
        // Create a new image with the same dimensions
        BufferedImage shadowImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Loop through the original image's pixels
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int pixel = original.getRGB(x, y);
                if (pixel != 0) { // Check if pixel is not transparent
                    Color originalColor = new Color(pixel, true);
                    int gray = (int) (originalColor.getRed() * 0.199 + originalColor.getGreen() * 0.187 + originalColor.getBlue() * 0.114);
                    Color shadowColor = new Color(gray / 2, gray / 2, gray / 2); // Darken the color for the shadow

                    shadowImage.setRGB(x, y, shadowColor.getRGB());
                }
            }
        }

        return shadowImage;
    }

}
