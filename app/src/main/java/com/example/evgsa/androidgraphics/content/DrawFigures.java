package com.example.evgsa.androidgraphics.content;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import com.example.evgsa.androidgraphics.MainActivity;
import com.example.evgsa.androidgraphics.R;
import com.example.evgsa.androidgraphics.logic.GameBase;
import com.example.evgsa.androidgraphics.logic.Settings;
import com.example.evgsa.androidgraphics.utils.AnimationTimer;
import com.example.evgsa.androidgraphics.utils.RecordBaseHelper;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * This class draws all the game table on screen
 */

public class DrawFigures
{
    private static Paint paint;
    private static int snakeColor;
    public static int gameTableBoundsX;
    public static int gameTableBoundsY;
    public static int width;
    public static int height;

    public static boolean isOneDrawn = false;
    private static boolean gamePaused = false;

    private static SQLiteDatabase db;
    private static Cursor cursor;
    public static String record;

    private static Bitmap gameUnpausedButton;
    private static Bitmap gamePausedButton;
    private static Bitmap settingsButton;
    private static Bitmap settingsArrow;
    public static Rect pauseButtonRect, settingsRect, settingsArrowRect;

    public static int settingsShown = 0; //0 means settings are not shown, 1 is that they are scrolling out, 2 is scrolling back in, 3 is shown entirely
    private static Canvas settingsCanvas;
    private static Settings settingsWindow;

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

            paint = new Paint();
            snakeColor = 255 << 24;

            gameUnpausedButton = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.pause_button);
            gamePausedButton = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.unpause_button);
            pauseButtonRect = new Rect();
            pauseButtonRect.set(canvas.getWidth() - 150, 50, canvas.getWidth() - 50, 150);

            settingsButton = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.settings);
            settingsButton = settingsButton.copy(Bitmap.Config.ARGB_8888, true); //converting bitmap into a mutable one

            settingsArrow = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.settings_arrow);
            settingsArrowRect = new Rect();
            settingsArrowRect.set(canvas.getWidth() / 2 - 100, 1, canvas.getWidth() / 2 + 100, 101); //the rect is turned upside down

            settingsCanvas = new Canvas(settingsButton);
            settingsWindow = new Settings(settingsCanvas, paint);

            isOneDrawn = true;
        }

        //Draws all the background (light-blue color)
        canvas.drawARGB(255, 188, 251, 245);

        paint.setColor(snakeColor);

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
            pauseButton = gameUnpausedButton;
        else
            pauseButton = gamePausedButton;
        canvas.drawBitmap(pauseButton, null, pauseButtonRect, paint);

        //Draw the settings arrow that scrolls the settings in and out
        canvas.drawBitmap(settingsArrow, null, settingsArrowRect, paint);

        //Draws the settings in case if they are not hided
        if(settingsShown != 0)
        {
            settingsWindow.drawLine();
            canvas.drawBitmap(settingsButton, null, settingsRect, paint);
        }
    }

    /**
     * The method scrolls settings out by moving settings and an arrow downwards
     * */
    public static void scrollSettingsOut()
    {
        settingsRect = new Rect(); //creating a new rect (it didn't need to exist before because it was unseen)
        settingsRect.set(width / 2 - 300, -101, width / 2 + 300, 0); //sets the rect's starting coordinates
        AnimationTimer settingsTimer = new AnimationTimer(settingsRect); //moving settings
        settingsTimer.moveY(100, 300);
        AnimationTimer settingsArrowTimer = new AnimationTimer(settingsArrowRect); //moving settings' arrow
        settingsArrowTimer.moveY(100, 300);
        settingsShown = 1; //settings are scrolling out
    }

    /**
     * The method scrolls settings in by moving settings and an arrow upwards
     * */
    public static void scrollSettingsIn()
    {
        AnimationTimer settingsTimer = new AnimationTimer(settingsRect); //moving settings
        settingsTimer.moveY(-100, 300);
        AnimationTimer settingsArrowTimer = new AnimationTimer(settingsArrowRect); //moving settings' arrow
        settingsArrowTimer.moveY(-100, 300);
        settingsShown = 2; //settings are scrolling in
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

    /**
     * Changes snake's color by changing snakeColor variable, which is used by draw method
     * @param a Color's alpha
     * @param r Color's red
     * @param g Color's green
     * @param b Color's blue
     * */
    public static void changeColorTo(int a, int r, int g, int b)
    {
        paint.setARGB(a, r, g, b);
        snakeColor = (a << 24) + (r << 16) + (g << 8) + b;
    }
}
























































