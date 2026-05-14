package me.roomy.flappyescape;

import javax.swing.*;

public class FlappyEscape
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        GamePanel gamePanel = new GamePanel();

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
