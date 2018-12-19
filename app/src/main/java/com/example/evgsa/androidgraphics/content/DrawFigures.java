package com.example.evgsa.androidgraphics.content;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.AlphabeticIndex;
import android.util.Log;

import com.example.evgsa.androidgraphics.MainActivity;
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
        {
            record = cursor.getString(cursor.getColumnIndex(RecordBaseHelper.KEY_RECORD));
            canvas.drawText("Рекорд: " + record, 60, 120, paint);
        }
    }

    public static void updateRecord()
    {
        db = MainActivity.helper.getReadableDatabase();
        cursor = db.query(RecordBaseHelper.TABLE_NAME, null, null, null, null, null, null);
    }
}
























































