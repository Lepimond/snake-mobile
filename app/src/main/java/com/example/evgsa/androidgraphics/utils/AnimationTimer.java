package com.example.evgsa.androidgraphics.utils;

import android.graphics.Rect;

import com.example.evgsa.androidgraphics.content.DrawFigures;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationTimer
{
    public TimerTask taskX;
    public TimerTask taskY;

    public boolean taskFinished = false;

    final Rect rect;

    /**
     * @param rect is rect, the coordinate of which will be changed in future
     * */
    public AnimationTimer(Rect rect)
    {
        this.rect = rect;
    }

    /**
     * The method slowly changes X coordinate of a rect, which makes it being moved upwards or downwards
     *
     * @param diffX is the diff between final coordinate and starting coordinate
     * @param time is a time by which the animation should be over (in milliseconds)
     * */
    public void moveX(final int diffX, int time)
    {
        final int startingX = rect.left;
        Timer timer = new Timer();

        taskX = new TimerTask()
        {
            @Override
            public void run()
            {
                int currentX = rect.left;

                if(diffX < 0)
                {
                    rect.left--;
                    rect.right--;
                    if((diffX + startingX)  >= currentX)
                    {
                        taskX.cancel();
                        changeSettingsRect(rect);
                    }
                }
                else
                {
                    rect.left++;
                    rect.right++;
                    if((diffX + startingX) <= currentX)
                    {
                        taskX.cancel();
                        changeSettingsRect(rect);
                    }
                }

            }
        };
        timer.schedule(taskX, time / Math.abs(diffX), time / Math.abs(diffX));
    }

    /**
     * The method slowly changes Y coordinate of a rect, which makes it being moved upwards or downwards
     *
     * @param diffY is the diff between final coordinate and starting coordinate
     * @param time is a time by which the animation should be over (in milliseconds)
     * */
    public void moveY(final int diffY, int time)
    {
        final int startingY = rect.top;
        Timer timer = new Timer();

        taskY = new TimerTask()
        {
            @Override
            public void run()
            {
                int currentY = rect.top;

                if(diffY < 0)
                {
                    rect.top--;
                    rect.bottom--;
                    if((diffY + startingY)  >= currentY)
                    {
                        taskY.cancel();
                        changeSettingsRect(rect);
                    }
                }
                else
                {
                    rect.top++;
                    rect.bottom++;
                    if((diffY + startingY) <= currentY)
                    {
                        taskY.cancel();
                        changeSettingsRect(rect);
                    }
                }
            }
        };
        timer.schedule(taskY, time / Math.abs(diffY), time / Math.abs(diffY));

    }

    /**
     * This method checks if the timer is working with settings' rect from DrawFigures and then does the job
     * @param rect is the rect that will be checked to be settings' rect
     * */
    private void changeSettingsRect(Rect rect)
    {
        if(rect == DrawFigures.settingsRect)
        {
            switch(DrawFigures.settingsShown)
            {
                case 1: //in case if the rect was scrolling out
                {
                    DrawFigures.settingsShown = 3;
                    break;
                }
                case 2: //in case if the rect was scrolling in
                {
                    DrawFigures.settingsRect = null; //deleting the rect because it's unseen anyway
                    DrawFigures.settingsShown = 0;
                    break;
                }
            }
        }

    }
}



















































