package com.example.evgsa.androidgraphics.utils;

import android.media.AudioManager;
import android.media.SoundPool;

import com.example.evgsa.androidgraphics.MainActivity;
import com.example.evgsa.androidgraphics.R;

/**
 * This class loads game sounds in the very beginning and plays them when needed
 * */

public class Sounds
{
    private static SoundPool pool;
    public static int eatSoundId, deathSoundId;

    public Sounds()
    {
        pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        eatSoundId = pool.load(MainActivity.instance, R.raw.eat, 1);
        deathSoundId = pool.load(MainActivity.instance, R.raw.death, 1);
    }

    public static void playSound(int soundId)
    {
        pool.play(soundId, 1.0F, 1.0F, 1, 0, 1.0F);
    }
}
