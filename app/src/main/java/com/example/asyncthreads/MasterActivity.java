package com.example.asyncthreads;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MasterActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        if ((id == R.id.menuAsync) && !(this instanceof AsyncActivity))
        {
            Intent intent = new Intent(this, AsyncActivity.class);
            startActivity(intent);
        }
        else if ((id == R.id.menuThreads) && !(this instanceof ThreadsActivity))
        {
            Intent intent = new Intent(this, ThreadsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}