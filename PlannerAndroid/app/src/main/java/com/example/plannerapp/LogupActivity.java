package com.example.plannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.Instant;

public class LogupActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        mToolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logup_menu,menu);

        return true;

    }
    public void onLogInClick(MenuItem view)
    {
        Intent intent = new Intent(LogupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

