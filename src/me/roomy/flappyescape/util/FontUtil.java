package me.roomy.flappyescape.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class FontUtil
{
    private BufferedImage FONTESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 8;
    public int w;
    public int h;
    private int wLetter;
    private int hLetter;

    private HashMap<Character, Point> charMap;

    public FontUtil(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("[*] Loading: " + file + "...");
        FONTESHEET = loadFont(file);

        wLetter = FONTESHEET.getWidth() / w;
        hLetter = FONTESHEET.getHeight() / h;

        loadSpriteArray();
        buildCharMap();
    }

    public FontUtil(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("[*] Loading: " + file + "...");
        FONTESHEET = loadFont(file);
        removeBackground();

        wLetter = FONTESHEET.getWidth() / w;
        hLetter = FONTESHEET.getHeight() / h;

        loadSpriteArray();
        buildCharMap();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wLetter = FONTESHEET.getWidth() / w;
    }

    public void setHeight(int i) {
        h = i;
        hLetter = FONTESHEET.getHeight() / h;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("[!] Error: Could not load file: " + file);
        }
        return sprite;
    }

    private void removeBackground() {
        int colors[] = { 0xFFFF00F2, 0xFFAD00A4 };
        int width = FONTESHEET.getWidth();
        int height = FONTESHEET.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = FONTESHEET.getRGB(x, y);

                boolean remove = false;
                for(int target : colors) {
                    if ((pixel | 0xFF000000) == target) {
                        remove = true;
                        break;
                    }
                }
                if(remove) {
                    FONTESHEET.setRGB(x, y, 0x00000000);
                } else {
                    FONTESHEET.setRGB(x,y, pixel);
                }
            }
        }
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++) {
                spriteArray[x][y] = getLetter(x, y);
            }
        }
    }

    public BufferedImage getFontSheet() {
        return FONTESHEET;
    }

    public BufferedImage getLetter(int x, int y) {
        return FONTESHEET.getSubimage(x * w, y * h, w, h);
    }

    private void buildCharMap() {
        charMap = new HashMap<>();

        String layout =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ\n" +
                        "abcdefghijklmnopqrstuvwxyz\n" +
                        "0123456789.,?!+-%=_()/\\\\<>:\n" +
                        ";\"'@#~*[]{}&^$€£|";

        String[] rows = layout.split("\n");

        for (int y = 0; y < rows.length; y++) {
            String row = rows[y];

            for (int x = 0; x < row.length(); x++) {
                charMap.put(row.charAt(x), new Point(x, y));
            }
        }
    }

    public BufferedImage getFont(char letter) {
        Point p = charMap.get(letter);

        if (p == null) {
            return null;
        }

        return getLetter(p.x, p.y);
    }
}