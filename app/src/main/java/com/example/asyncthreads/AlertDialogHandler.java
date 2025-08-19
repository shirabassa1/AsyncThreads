package com.example.asyncthreads;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogHandler
{
    private AlertDialog.Builder adb;
    private AlertDialog ad;

    AlertDialogHandler(Context context, DialogInterface.OnClickListener stopPlayingAction)
    {
        adb = new AlertDialog.Builder(context);
        create(stopPlayingAction);
    }

    private void create(DialogInterface.OnClickListener stopPlayingAction)
    {
        adb.setTitle("Stop playing sound");
        adb.setMessage("Are you sure you want to stop playing this sound?");

        adb.setPositiveButton("Yes", stopPlayingAction);
        adb.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });

        ad = adb.create();
    }

    public void show()
    {
        if (ad != null)
        {
            ad.show();
        }
    }
}
