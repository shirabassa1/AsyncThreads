package com.example.asyncthreads;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;

public class PlaySoundAsyncTask extends AsyncTask<Void, Void, Void>
{
    private final WeakReference<AsyncActivity> activityRef;
    private AsyncActivity activity;
    private MediaPlayer player;

    public PlaySoundAsyncTask(AsyncActivity context)
    {
        activityRef = new WeakReference<>(context);
        activity = activityRef.get();
    }

    @Override
    protected Void doInBackground(Void... aVoid)
    {
        if (activity == null)
        {
            return null;
        }

        player = MediaPlayer.create(activity, R.raw.sound);
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