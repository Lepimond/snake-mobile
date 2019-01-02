package com.example.evgsa.androidgraphics.utils;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.evgsa.androidgraphics.content.DrawFigures;
import com.example.evgsa.androidgraphics.logic.GameBase;

/**
 * ScreenTouchListener processes touches on the screen and
 * changes the snake's direction according to touch's coordinates, or pauses the game
 * */

public class ScreenTouchListener implements View.OnTouchListener
{
    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int x = (int)event.getX();
            int y = (int)event.getY();

            int centerX = DrawFigures.width / 2;
            int centerY = DrawFigures.height / 2;

            if((x > (DrawFigures.width - 150)) && (x < (DrawFigures.width - 50)) && (y > 50) && (y < 150)) //checking if a player presses on pause button
            {
                GameBase.pause();
                DrawFigures.setPauseButton();
            }
            else if((x > DrawFigures.width / 2 - 300) && (x < DrawFigures.width / 2 + 300) && (y > 0) && (y < 100)) //checking if a player touches at settings zone
            {
                switch (DrawFigures.settingsShown)
                {
                    case 0: //if settings are hided
                    {
                        if((x > DrawFigures.width / 2 - 100) && (x < DrawFigures.width / 2 + 100) && (y > 0) && (y < 100)) //if player touches on settings arrow
                        {
                            DrawFigures.settingsShown = 1;
                            DrawFigures.scrollSettingsOut();
                        }
                        break;
                    }
                    case 1:
                    {
                        break;
                    }
                    case 2:
                    {
                        break;
                    }
                    case 3:
                    {
                        //TODO DrawFigures.settingsShown = 2;
                        //TODO DrawFigures.scrollSettingsIn();
                        colorFromTouch(x);
                        break;
                    }
                }

            }
            else if((x > DrawFigures.width / 2 - 100) && (x < DrawFigures.width / 2 + 100) && (y > 100) && (y < 200) && (DrawFigures.settingsShown == 3)) //if player touches on settings arrow when settings are entirely shown
            {
                DrawFigures.scrollSettingsIn();
            }
            else if(GameBase.direction == 0 || GameBase.direction == 2) //changing snake's direction
            {
                if(x < centerX)
                    GameBase.direction = 3;
                else
                    GameBase.direction = 1;
            }
            else if(GameBase.direction == 1 || GameBase.direction == 3)
            {
                if(y < centerY)
                    GameBase.direction = 0;
                else
                    GameBase.direction = 2;
            }
        }



        return false;
    }

    private void colorFromTouch(int touchX)
    {
        int height = DrawFigures.settingsRect.height(); //the height of settings rect, also sides of black rects that surround colored rects inside settings
        touchX = touchX - (DrawFigures.width - DrawFigures.settingsRect.width()) / 2;
        //Checking what rect is touched and changing snake's color:
        if(touchX < height) //light-yellow color
        {
            DrawFigures.changeColorTo(255, 232, 243, 144);
        }
        else if(touchX < height * 2) //dark-violet color
        {
            DrawFigures.changeColorTo(255, 70, 45, 113);
        }
        else if(touchX < height * 3) //dark-red color
        {
            DrawFigures.changeColorTo(255, 208, 62, 47);
        }
        else if(touchX < height * 4) //green color
        {
            DrawFigures.changeColorTo(255, 29, 90, 29);
        }
        else if(touchX < height * 5) //turquoise color
        {
            DrawFigures.changeColorTo(255, 31, 33, 204);
        }
        else //black color
        {
            DrawFigures.changeColorTo(255, 0, 0, 0);
        }
    }
}




















































