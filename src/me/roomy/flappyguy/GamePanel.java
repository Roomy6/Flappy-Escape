package me.roomy.flappyguy;

import me.roomy.flappyguy.entity.Entity;
import me.roomy.flappyguy.entity.Guard;
import me.roomy.flappyguy.entity.Player;
import me.roomy.flappyguy.handler.KeyHandler;
import me.roomy.flappyguy.object.SuperObject;
import me.roomy.flappyguy.ui.UI;
import me.roomy.flappyguy.util.BackgroundManager;
import me.roomy.flappyguy.util.CollisionManager;
import me.roomy.flappyguy.util.ObjectSetter;
import me.roomy.flappyguy.util.ScaleManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable
{
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

    public static final String GAME_NAME = "Flappy Escape";
    public static final String VERSION = "Beta 0.0.3";
    public static final String WINDOW_TITLE = GAME_NAME + " - " + VERSION;

    public final int TILE_SIZE = 38;

    private int realFPS;
    private double updateSeconds;
    private int renderedObjects;

    /* Base setup */
    Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler(this);

    /* Managers */
    public ObjectSetter objectSetter = new ObjectSetter(this);
    public CollisionManager collisionManager = new CollisionManager(this);
    public BackgroundManager backgroundManager = new BackgroundManager(this);

    /* Entities and Objects */
    public Player player = new Player(this, keyHandler);
    public Guard guard = new Guard(this, keyHandler);
    public SuperObject[] objects = new SuperObject[100];
    public Entity[] entity = new Entity[20];

    /* UI */
    public UI ui = new UI(this);

    /* Game States */
    public int gameState;
    public final int mainMenuState = 0;
    public final int playState = 1;
    public final int gameOverState = 2;

    public GamePanel()
    {
        System.out.println("[i] Setting up Game Window.");

        this.setPreferredSize(SCREEN_SIZE);

        /* Debug Layer */
        this.setBackground(Color.DARK_GRAY);

        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        System.out.println("[i] Setting up game.");

        /* TEMP */
        objectSetter.setObject();
        gameState = mainMenuState;
    }

    public void startGameThread()
    {
        System.out.println("[i] Starting Game Thread.");
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        int targetFPS = 60;
        double drawInterval = 1000000000.0 / targetFPS; // 0.0166... seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {

                update();
                repaint();

                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                realFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }

            try {
                double remainingTime = (lastTime - System.nanoTime() + drawInterval) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update()
    {
        long updateStart = System.nanoTime();

        player.update();
        guard.update();
        collisionManager.update();
        backgroundManager.update();

        long updateEnd = System.nanoTime();
        long passed = updateEnd - updateStart;
        updateSeconds = passed / 1_000_000.0;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = System.nanoTime();

        backgroundManager.draw(g2);

        if (player != null)
        {
            /* Draw pipes first before player */
            for (int i = 0; i < objects.length; i++)
            {
                renderedObjects = i;
                if(objects[i] != null)
                {
                    objects[i].draw(g2, this);

                    if(!player.isCollided && !player.hasCollided && gameState == playState) {
                        objects[i].worldX -= player.speed / 2;
                    }

                    // DEBUG
                    if(keyHandler.debug) {
                        g2.setColor(Color.RED);

                        int x = objects[i].worldX + objects[i].boundingBox.x;
                        int topY = objects[i].worldY;
                        int bottomY = objects[i].worldY + objects[i].gapSize;

                        /* Top Pipe */
                        g2.drawRect(
                                x,
                                topY + objects[i].boundingBox.y,
                                objects[i].boundingBox.width,
                                objects[i].boundingBox.height
                        );

                        /* Bottom Pipe */
                        g2.drawRect(
                                x,
                                bottomY + objects[i].boundingBox.y,
                                objects[i].boundingBox.width,
                                objects[i].boundingBox.height
                        );

                        g2.setColor(Color.YELLOW);

                        /* Top Pipe*/
                        g2.drawRect(
                                x,
                                0,
                                objects[i].boundingBox.width,
                                topY
                        );

                        /* Bottom Pipe */
                        g2.drawRect(
                                x,
                                bottomY + objects[i].boundingBox.height,
                                objects[i].boundingBox.width,
                                WINDOW_HEIGHT - bottomY
                        );
                    }
                }
            }

            guard.draw(g2);
            player.draw(g2);
            ui.draw(g2);
        }

        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        double ms = passed / 1_000_000.0;

        if (keyHandler.debug) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 12));

            int x = 10;
            int y = 20;
            int lineHeight = 15;

            g2.drawString("FPS: " + realFPS, x, y);
            g2.drawString("Update MS: " + String.format("%.5f", updateSeconds), x, y += lineHeight);
            g2.drawString("Collision: " + player.isCollided, x, y += lineHeight);
            g2.drawString("Y Velocity: " + String.format("%.2f", player.yVelocity), x, y += lineHeight);
            g2.drawString("World Y: " + (int)player.worldY, x, y += lineHeight);
            g2.drawString("Flap Pressed: " + keyHandler.flapPressed, x, y += lineHeight);
            g2.drawString("Game State: " + gameState, x, y += lineHeight);
            g2.drawString("Rendered Objects: " + renderedObjects, x, y += lineHeight);

            g.setColor(Color.WHITE);
        }

        /* Helps with the stupid no input lag or whatever */
        Toolkit.getDefaultToolkit().sync();
    }

    public BufferedImage loadImage(String path) {
        ScaleManager scaleManager = new ScaleManager();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
            image = scaleManager.scaledImage(image, TILE_SIZE, TILE_SIZE);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
