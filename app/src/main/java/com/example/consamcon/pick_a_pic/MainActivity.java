package com.example.consamcon.pick_a_pic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void loadScores(View view){
        Intent scoresIntent = new Intent(MainActivity.this, HomeActivity.class);

        // Start the new activity
        startActivity(scoresIntent);
    }
    public void loadSettings(View view){
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);

        // Start the new activity
        startActivity(settingsIntent);
    }
}

