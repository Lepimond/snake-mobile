package com.example.evgsa.androidgraphics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import com.example.evgsa.androidgraphics.content.DrawFigures;

/**
 * Works at all the technical part of drawing.
 */

public class DrawThread extends Thread
{
    private Canvas canvas;

    private boolean running = false;
    private SurfaceHolder surfaceHolder;

    /**
     * @param surfaceHolder A holder whose surface is used to draw the game
     * */
    public DrawThread(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        while (running)
        {
            canvas = null;
            try
            {
                canvas = surfaceHolder.lockCanvas(null); //first the program gets the canvas
                if (canvas == null) //then it waits for it not to be null (just for a case)
                    continue;
                DrawFigures.draw(canvas); //and draws the game on the canvas
            } finally {    //even if there was an exception, program has to send canvas back
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}





















































