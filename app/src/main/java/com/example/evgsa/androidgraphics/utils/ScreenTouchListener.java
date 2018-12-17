package com.example.evgsa.androidgraphics.utils;

import android.view.MotionEvent;
import android.view.View;

import com.example.evgsa.androidgraphics.content.DrawFigures;
import com.example.evgsa.androidgraphics.logic.GameBase;

/**
 * ScreenTouchListener processes touches on the screen and
 * changes the snake's direction according to touch's coordinates
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

            if(GameBase.direction == 0 || GameBase.direction == 2)
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
}




















































