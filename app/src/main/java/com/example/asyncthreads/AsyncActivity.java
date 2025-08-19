package com.example.asyncthreads;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AsyncActivity extends MasterActivity
{
    public Button btnStart, btnStop;
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
                new AlertDialogHandler(AsyncActivity.this,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            stopPlaying();
                        }
                    }).show();
            }
        });
    }

    public void startPlaying()
    {
        if ((playSoundTask == null) || (playSoundTask.getStatus() == AsyncTask.Status.FINISHED))
        {
            playSoundTask = new PlaySoundAsyncTask(this);
            playSoundTask.execute();
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        }
    }

    public void stopPlaying()
    {
        if (playSoundTask != null)
        {
            playSoundTask.cancel(true);
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        }
    }
}