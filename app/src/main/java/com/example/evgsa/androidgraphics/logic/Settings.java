package com.example.evgsa.androidgraphics.logic;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Settings
{
    Canvas settingsCanvas;
    Paint paint;

    public Settings(Canvas settingsCanvas, Paint paint)
    {
        this.settingsCanvas = settingsCanvas;
        this.paint = paint;
    }

    public void drawLine()
    {
        float width = settingsCanvas.getWidth();
        float height = settingsCanvas.getHeight();
        float squareSide = height; //a side of a black square that surrounds colored square

        //Drawing the rects one by one on the canvas from constructor
        paint.setARGB(255, 232, 243, 144); //light-yellow color
        settingsCanvas.drawRect(20, 20, squareSide - 10, squareSide - 20, paint);
        paint.setARGB(255, 70, 45, 113); //dark-violet color
        settingsCanvas.drawRect(squareSide + 20, 20, squareSide * 2 - 10, squareSide - 20, paint);
        paint.setARGB(255, 208, 62, 47); //dark-red color
        settingsCanvas.drawRect(squareSide * 2 + 20, 20, squareSide * 3 - 10, squareSide - 20, paint);
        paint.setARGB(255, 29, 90, 29); //green color
        settingsCanvas.drawRect(squareSide * 3 + 20, 20, squareSide * 4 - 10, squareSide - 20, paint);
        paint.setARGB(255, 31, 33, 204); //turquoise color
        settingsCanvas.drawRect(squareSide * 4 + 20, 20, squareSide * 5 - 10, squareSide - 20, paint);
    }
}
















































