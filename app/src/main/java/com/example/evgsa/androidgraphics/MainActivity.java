package com.example.evgsa.androidgraphics;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.evgsa.androidgraphics.logic.GameBase;
import com.example.evgsa.androidgraphics.utils.RecordBaseHelper;
import com.example.evgsa.androidgraphics.utils.ScreenTouchListener;
import com.example.evgsa.androidgraphics.utils.Sounds;

/**
 * The main class.
 * Sets window's settings, sounds and database, also restarts game when needed
 */

public class MainActivity extends AppCompatActivity
{
    public static MainActivity instance;
    public static RecordBaseHelper helper;
    public static Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Hiding the status and navigation bars so they don't mess up the game
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        instance = this;
        Sounds sounds = new Sounds();
        helper = new RecordBaseHelper();
        resources = getResources();

        DrawView draw = new DrawView(this);
        draw.setOnTouchListener(new ScreenTouchListener());
        setContentView(draw);
    }


    public static void restartGame()
    {
        GameBase gameBase = new GameBase();
    }


    /**
     * Hides the bars again
     * */
    @Override
    public void onResume()
    {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        super.onResume();
    }
}























































