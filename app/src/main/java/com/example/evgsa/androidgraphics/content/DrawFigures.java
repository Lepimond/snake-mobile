package com.example.evgsa.androidgraphics.content;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.icu.text.AlphabeticIndex;
import android.util.Log;

import com.example.evgsa.androidgraphics.MainActivity;
import com.example.evgsa.androidgraphics.R;
import com.example.evgsa.androidgraphics.logic.GameBase;
import com.example.evgsa.androidgraphics.utils.RecordBaseHelper;

/**
 * This class draws all the game table on screen
 */

public class DrawFigures
{
    private static Paint paint;
    public static int gameTableBoundsX;
    public static int gameTableBoundsY;
    public static int width;
    public static int height;

    public static boolean isOneDrawn = false;
    private static boolean gamePaused = false;

    private static SQLiteDatabase db;
    private static Cursor cursor;
    public static String record;

    /**
     * @param canvas Canvas everything will be drawn on
     * */
    public static void draw(Canvas canvas)
    {
        //if it's the very first frame then the program initialises some variables and starts the game
        if (!isOneDrawn)
        {
            width = canvas.getWidth();
            height = canvas.getHeight();

            db = MainActivity.helper.getReadableDatabase();
            cursor = db.query(RecordBaseHelper.TABLE_NAME, null, null, null, null, null, null);
            if(cursor.moveToLast())
                record = cursor.getString(cursor.getColumnIndex(RecordBaseHelper.KEY_RECORD));

            gameTableBoundsX = canvas.getWidth() / 40; //One snake's pixel will be 40px*40px on any device
            gameTableBoundsY = canvas.getHeight() / 40;
            GameBase gameBase = new GameBase(); //Starts the game

            isOneDrawn = true;
        }

        //Draws all the background (light-blue color)
        canvas.drawARGB(255, 188, 251, 245);

        paint = new Paint();
        paint.setARGB(255, 0, 0, 0);

        //Goes through all the screen and draws black squares for all activated cells (draws the snake)
        for (int i = 0; i < gameTableBoundsX; i++)
        {
            for (int j = 0; j < gameTableBoundsY; j++)
            {
                if (GameBase.cells[i][j].isCellActivated)
                    canvas.drawRect(i * 40, j * 40, (i + 1) * 40, (j + 1) * 40, paint);
            }
        }
        //Draws the food with green color
        paint.setColor(Color.GREEN);
        canvas.drawRect(GameBase.foodX * 40, GameBase.foodY * 40, (GameBase.foodX + 1) * 40, (GameBase.foodY + 1) * 40, paint);

        //Writes the current score on top of  the screen
        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(50);
        canvas.drawText( "Счёт: " + String.valueOf(GameBase.snakeLength - 5), 60, 60, paint);

        //Writes the record below the current score
        if(cursor.moveToLast())
            canvas.drawText("Рекорд: " + record, 60, 120, paint);

        //Draws the pause button in a right-top corner of the screen
        Bitmap pauseButton;
        if(!gamePaused) //button's appearance depends on gamePaused
            pauseButton = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.pause_button);
        else
            pauseButton = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.unpause_button);
        Rect rect = new Rect();
        rect.set(canvas.getWidth() - 150, 50, canvas.getWidth() - 50, 150);
        canvas.drawBitmap(pauseButton, null, rect, paint);
    }

    /**
     * The method reverse gamePaused variable, which will affect pause button's appearance
     * */
    public static void setPauseButton()
    {
        gamePaused = !gamePaused;
    }

    /**
     * The method updates cursor so it'll read from an updated base and then updates record variable
     * */
    public static void updateRecord()
    {
        cursor = db.query(RecordBaseHelper.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToLast();
        record = cursor.getString(cursor.getColumnIndex(RecordBaseHelper.KEY_RECORD));
    }
}
























































