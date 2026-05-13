package me.roomy.flappyguy;

import javax.swing.*;
import java.awt.*;

public class FlappyGuy
{
    public static void main(String[] args)
    {
        GamePanel gamePanel;

        JFrame frame = new JFrame();
        gamePanel = new GamePanel();

        frame.setTitle(GamePanel.WINDOW_TITLE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gamePanel);
        frame.pack();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
