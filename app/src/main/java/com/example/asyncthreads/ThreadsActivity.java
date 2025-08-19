package com.example.asyncthreads;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.ref.WeakReference;

public class ThreadsActivity extends MasterActivity
{
    private Thread playSoundThread;
    private Runnable playSoundRunnable;
    private Handler handler;
    private MediaPlayer player;
    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        init();
    }

    private void init()
    {
        handler = new Handler(Looper.getMainLooper());

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        setButtons();
    }

    private void setButtons()
    {
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startPlaying();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                stopPlaying();
            }
        });
    }

    private Runnable createPlaySoundRunnable()
    {
        return () ->
        {
            player = MediaPlayer.create(this, R.raw.sound);
            player.start();

            try
            {
                while (!Thread.currentThread().isInterrupted() && player.isPlaying())
                {
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }

            if (!player.isPlaying())
            {
                player.release();
                player = null;

                handler.post(() ->
                {
                    btnStart.setEnabled(true);
                    btnStop.setEnabled(false);
                });
            }
        };
    }

    public void startPlaying()
    {
        playSoundRunnable = createPlaySoundRunnable();
        playSoundThread = new Thread(playSoundRunnable);
        playSoundThread.start();

        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }

    public void stopPlaying()
    {
        if (playSoundThread != null && player != null)
        {
            playSoundThread.interrupt();

            if (player.isPlaying())
            {
                player.stop();
            }

            player.release();
            player = null;
        }

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }
}