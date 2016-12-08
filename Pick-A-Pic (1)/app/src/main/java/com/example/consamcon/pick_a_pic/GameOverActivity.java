package com.example.consamcon.pick_a_pic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView t = (TextView)(findViewById(R.id.gameover_high_score));
        t.setText("Score: " + HomeActivity.highScore);

    }

    public void loadMainMenu (View view) {

        this.finish();

    }

    public void shareScore(View view) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ("I just got a new high score of " + HomeActivity.highScore + " in Pick-A-Pic. Can you beat me?!"));
        startActivity(emailIntent);
    }
}



