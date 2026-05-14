package me.roomy.flappyescape.ui;

import me.roomy.flappyescape.GamePanel;

import java.awt.*;

public class UI
{
    GamePanel game;
    Graphics2D g2;

    public UI(GamePanel game)
    {
        this.game = game;
    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;

        /* Title Screen */
        if(game.gameState == game.mainMenuState)
        {
            drawMainMenuScreen();
        }

        /* Play State */
        if(game.gameState == game.playState)
            drawGameUI();

        /* Pause State */

        /* Game Over state */
        if(game.gameState == game.gameOverState)
        {
            drawGameOverScreen();
        }
    }

    public void drawGameUI()
    {
        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        //g2.drawString("Score: " + game.scoreManager.score, 5, 15);

        g2.setFont(new Font("Monospaced", Font.BOLD, 12));
        g2.drawString("Press l for debug", 5, GamePanel.WINDOW_HEIGHT - 5);
    }

    public void drawMainMenuScreen()
    {
        String titleText = GamePanel.GAME_NAME;
        String versionText = GamePanel.VERSION;
        String subText = "By Roomy";
        String playText = "Press Space to Play!";

        g2.setColor(Color.BLACK);

        g2.setFont(new Font("Monospaced", Font.BOLD, 50));
        FontMetrics fmTitle = g2.getFontMetrics();
        int titleX = (GamePanel.WINDOW_WIDTH - fmTitle.stringWidth(titleText)) / 2;
        g2.drawString(titleText, titleX + 5, 50 + 5);

        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        FontMetrics fmSub = g2.getFontMetrics();
        int subTextX = (GamePanel.WINDOW_WIDTH - fmSub.stringWidth(subText)) / 2;
        g2.drawString(subText, subTextX - 100 + 3, 80 + 3);

        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        FontMetrics fmVersion = g2.getFontMetrics();
        int versionX = (GamePanel.WINDOW_WIDTH - fmVersion.stringWidth(versionText)) / 2;
        g2.drawString(versionText, versionX + 100 + 3, 80 + 3);

        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
        FontMetrics fmPlay = g2.getFontMetrics();
        int playTextX = (GamePanel.WINDOW_WIDTH - fmPlay.stringWidth(playText)) / 2;
        g2.drawString(playText, playTextX + 5, 200 + 5);



        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Monospaced", Font.BOLD, 50));
        g2.drawString(titleText, titleX, 50);

        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2.drawString(subText, subTextX - 100, 80);

        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2.drawString(versionText, versionX + 100, 80);

        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
        g2.drawString(playText, playTextX, 200);
    }

    public void drawGameOverScreen()
    {
        Color c = new Color(50, 50, 50, 150);
        g2.setColor(c);
        g2.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2.drawString("Game Over!", (GamePanel.WINDOW_WIDTH / 2) - 30 * 2, GamePanel.WINDOW_HEIGHT / 3);
        g2.drawString("Press space to play again", (GamePanel.WINDOW_WIDTH / 2) - 70 * 2, GamePanel.WINDOW_HEIGHT / 2);
    }
}
