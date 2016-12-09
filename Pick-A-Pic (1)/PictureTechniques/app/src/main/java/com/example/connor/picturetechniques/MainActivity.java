package com.example.connor.picturetechniques;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Handler myHandler = new Handler();
    int current = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Runnable timedTask = new Runnable() {
                    public void run() {
                        cycleImage();
                    }
                };
                new Thread(timedTask).start();
            }
        });


    }

    int res = R.drawable.brady4;
    private void cycleImage() {
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        while(current >= 0) {
            if (current == 10) res = R.drawable.brady9;
            if (current == 9) res = R.drawable.brady8;
            if (current == 8) res = R.drawable.brady7;
            if (current == 7) res = R.drawable.brady6;
            if (current == 6) res = R.drawable.brady5;
            if (current == 5) res = R.drawable.brady4;
            if (current == 4) res = R.drawable.brady3;
            if (current == 3) res = R.drawable.brady2;
            if (current == 2) res = R.drawable.brady1;
            if (current == 1) res = R.drawable.brady0;
            current--;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    image.setImageResource(res);
                }
            });
        }
    }


}
