package com.example.evgsa.androidgraphics.logic;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.evgsa.androidgraphics.MainActivity;
import com.example.evgsa.androidgraphics.content.DrawFigures;
import com.example.evgsa.androidgraphics.utils.RecordBaseHelper;
import com.example.evgsa.androidgraphics.utils.Sounds;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.evgsa.androidgraphics.content.DrawFigures.gameTableBoundsX;
import static com.example.evgsa.androidgraphics.content.DrawFigures.gameTableBoundsY;

/**
 * This class is where all the game logic is processed
 * */

public class GameBase
{
    private static Random rand = new Random();
    private static boolean hasSnakeEaten = false;

    public static boolean squaresChanged = false;
    public static int direction = 2;
    public static int snakeLength = 5;
    public static Cell[][] cells = new Cell[gameTableBoundsX][gameTableBoundsY];

    static int latestX = 3;
    static int latestY = 3 + (snakeLength - 1);

    public static int foodX = rand.nextInt(gameTableBoundsX);
    public static int foodY = rand.nextInt(gameTableBoundsY);

    private static Timer timer;
    private static TimerTask timerTask;
    private static boolean taskPaused = false;

    /**
     * All the game background logic starts working from the moment when GameBase object is created
     * */
    public GameBase()
    {
        snakeLogic();
    }

    private void snakeLogic()
    {
        for(int i = 0; i < gameTableBoundsX; i++)
        {
            for(int j = 0; j < gameTableBoundsY; j++)
            {
                cells[i][j] = new Cell(); //initialising all the Cell's array
            }
        }

        for(int i = 0; i < snakeLength; i++)
        {
            cells[3][3 + i].activate(i); //activating the cells in the corner of screen (making the first snake)
        }

        timer = new Timer();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                if(!taskPaused) //All the 'run' block works only if the game isn't paused (if the game's paused, then it should stop making any progress)
                {
                    boolean taskCancelled = false;

                    //newX amd newY are coordinates of the next cell to be lit up
                    int newX = latestX;
                    int newY = latestY;
                    switch (direction) //What cell will be lit up next depends on snake's direction
                    {
                        case (0):
                        {
                            newY--;
                            break;
                        }
                        case (1):
                        {
                            newX++;
                            break;
                        }
                        case (2):
                        {
                            newY++;
                            break;
                        }
                        case (3):
                        {
                            newX--;
                            break;
                        }
                    }

                    //Making the snake to appear on the opposite side of game table as if it reaches the edge
                    if(newX < 0)
                        newX += gameTableBoundsX;
                    if(newY < 0)
                        newY += gameTableBoundsY;
                    if(newX >= gameTableBoundsX)
                        newX %= gameTableBoundsX;
                    if(newY >= gameTableBoundsY)
                        newY %= gameTableBoundsY;

                    //If snakes its itself, than the timer stops and the game restarts
                    if(cells[newX][newY].isCellActivated)
                    {
                        this.cancel();
                        taskCancelled = true;
                        lose();
                    }

                    if(!taskCancelled) //This block doesn't do anything if the player has lost
                    {
                        if(newX == foodX && newY == foodY) //If snake meets food, it eats the food
                        {
                            eatFood();
                        }
                        cells[newX][newY].activate();
                        latestX = newX;
                        latestY = newY;

                        for (int i = 0; i < gameTableBoundsX; i++)
                        {
                            for (int j = 0; j < gameTableBoundsY; j++)
                            {
                                cells[i][j].update(hasSnakeEaten);
                            }
                        }

                        hasSnakeEaten = false;
                    }
                }
            }
        };



        timer.schedule(timerTask, 1000 / 20, 1000 / 20); //Period argument entirely controls all the game speed
    }

    /**
     * The method reverses taskPaused condition
     * */
    public static void pause()
    {
        taskPaused = !taskPaused;
    }

    /**
     * This method sets all variables to their default values,
     * deactivated all cells and restarts the game
     * */
    private static void lose()
    {
        rand = new Random();
        hasSnakeEaten = false;

        squaresChanged = false;
        direction = 2;
        snakeLength = 5;
        for (int i = 0; i < gameTableBoundsX; i++)
        {
            for (int j = 0; j < gameTableBoundsY; j++)
            {
                cells[i][j].deactivate();
            }
        }

        latestX = 3; //Placing snake in it's default position
        latestY = 3 + (snakeLength - 1);

        foodX = rand.nextInt(gameTableBoundsX);
        foodY = rand.nextInt(gameTableBoundsY);

        Sounds.playSound(Sounds.deathSoundId); //Playing sound of dying

        MainActivity.restartGame();
    }

    private static void eatFood()
    {
        hasSnakeEaten = true;
        snakeLength++;
        foodX = rand.nextInt(gameTableBoundsX); //generating new food
        foodY = rand.nextInt(gameTableBoundsY);
        while(cells[foodX][foodY].isCellActivated) //generating the food again if it was generated inside the snake
        {
            foodX = rand.nextInt(gameTableBoundsX);
            foodY = rand.nextInt(gameTableBoundsY);
        }

        //If snake's length is the new record, then the program updates
        //database, and then calls DrawFigures update method
        if((snakeLength - 5) > Integer.parseInt(DrawFigures.record))
        {
            SQLiteDatabase db = MainActivity.helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(RecordBaseHelper.KEY_RECORD, snakeLength - 5);
            db.insert(RecordBaseHelper.TABLE_NAME, null, values);
            DrawFigures.updateRecord();
        }

        Sounds.playSound(Sounds.eatSoundId); //Playing sound of eating
    }
}



















































