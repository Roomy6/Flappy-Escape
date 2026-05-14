package me.roomy.flappyescape.util;

import me.roomy.flappyescape.GamePanel;

public class ScoreManager
{
    GamePanel game;

    public int gameScore;
    public int coinScore;
    public int highScore;

    public ScoreManager(GamePanel game)
    {
        this.game = game;

        highScore = 0;
        resetScores();
    }

    public void resetScores()
    {
        gameScore = 0;
        coinScore = 0;
    }

    public void update()
    {

    }
}
