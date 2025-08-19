package com.example.asyncthreads;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlaySoundAsyncTask extends AsyncTask<Void, Void, Void>
{
    private static final String TAG = "PlaySoundAsyncTask";
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private final WeakReference<AsyncActivity> activityRef;
    private MediaPlayer player;

    public PlaySoundAsyncTask(AsyncActivity context)
    {
        activityRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... aVoid)
    {
        AsyncActivity activity = activityRef.get();
        if (activity == null)
        {
            return null;
        }

        player = MediaPlayer.create(activity, R.raw.sound);

        player.setOnCompletionListener(mp ->
        {
            isRunning.set(false);
        });

        player.start();

        while (!isCancelled() && player.isPlaying())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        if (!player.isPlaying())
        {
            activity.runOnUiThread(() ->
            {
                activity.btnStart.setEnabled(true);
                activity.btnStop.setEnabled(false);
            });

            player.release();
            player = null;
        }

        return null;
    }

    @Override
    protected void onCancelled()
    {
        if (player != null)
        {
            if (player.isPlaying())
            {
                player.stop();
            }

            player.release();
            player = null;
        }

        super.onCancelled();
    }
}