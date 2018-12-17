package com.example.evgsa.androidgraphics.logic;

/**
 * This class describes a 'cell' - one pixel at game table
 * */

public class Cell
{
    private int timeLeft;
    public boolean isCellActivated = false;

    /**
     * This method decreases the time left for cell to be lit up
     * (that's a realisation of snake's tale to disappear with time)
     * */
    public void update(boolean hasSnakeEaten)
    {
        //if snake has eaten food then the time of being lit up shouldn't decrease
        //(and that's how snake becomes longer)
        if(!hasSnakeEaten && (timeLeft != 0))
        {
            timeLeft--;
        }
        else if (timeLeft == 0)
        {
            isCellActivated = false;
        }
    }

    public void activate()
    {
        timeLeft = GameBase.snakeLength;
        isCellActivated = true;
    }

    public void deactivate()
    {
        isCellActivated = false;
    }

    public void activate(int timeLeft)
    {
        this.timeLeft = timeLeft;
        isCellActivated = true;
    }
}
