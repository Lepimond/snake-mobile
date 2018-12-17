package com.example.evgsa.androidgraphics;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * This class makes the draw thread and a callback, also responds to surface's events
 * */
public class DrawView extends SurfaceView implements SurfaceHolder.Callback
{


    private DrawThread drawThread;

    public DrawView(Context context)
    {
        super(context);
        getHolder().addCallback(this); //registering this class so it's methods are called when needed
    }

    /**
     * This runs the thread immediately after surface is created
     * @param holder The holder is transferred to drawThread
     * */
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        drawThread = new DrawThread(holder);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    /**
     * Waits for drawThread to die
     * @param holder is not used here
     * */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(retry)
        {
            try
            {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
}




























































