package com.example.asyncthreads;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncActivity extends MasterActivity
{
    private static final String TAG = "AsyncActivity";
    private Button btnStart, btnStop;
    private PlaySoundAsyncTask playSoundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        init();
    }

    private void init()
    {
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }

    public void startPlaying(View view)
    {
        if ((playSoundTask == null) || (playSoundTask.getStatus() == AsyncTask.Status.FINISHED))
        {
            playSoundTask = new PlaySoundAsyncTask(this);
            playSoundTask.execute();
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        }
    }

    public void stopPlaying(View view)
    {
        if (playSoundTask != null)
        {
            playSoundTask.cancel(true);
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        }
    }
}