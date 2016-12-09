package com.example.consamcon.pick_a_pic;

import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.consamcon.pick_a_pic.data.ImageContract;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Handler myHandler = new Handler();
    private Random rand = new Random();

    int current = 10;
    int resID;
    int i, correctIndex;
    int roundNumber = 1;
    int score;
    boolean roundDone = false;
    ArrayList<String> randRefList;
    ArrayList<String> randDiscList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        Cursor cursor = getRandom();
        cursor.moveToFirst();
        randRefList = getRefArray(cursor);
        randDiscList = getDiscArray(cursor);
        score = 0;
        i = 0;
        resID = getResources().getIdentifier(randRefList.get(i) + "10", "drawable", getPackageName());

        imageView.setImageResource(resID);
        updateOptions();

        Runnable timedTask = new Runnable() {
            public void run() {
                cycleImage();
            }
        };
        new Thread(timedTask).start();
    }

    private void updateOptions() {
        String correct = randDiscList.get(i);
        int option1index, option2index;
        do {
            option1index = rand.nextInt(randDiscList.size());
            option2index = rand.nextInt(randDiscList.size());
        } while(option1index == option2index || option1index == i || option2index == i);
        String wrong1 = randDiscList.get(option1index);
        String wrong2 = randDiscList.get(option2index);
        ArrayList<String> choices = new ArrayList<String>();
        choices.add(wrong1);
        choices.add(wrong2);
        correctIndex = rand.nextInt(3);
        choices.add(correctIndex, correct);

        TextView t1, t2, t3;
        t1 = (TextView) findViewById(R.id.option1);
        t2 = (TextView) findViewById(R.id.option2);
        t3 = (TextView) findViewById(R.id.option3);

        t1.setText(choices.get(0));
        t2.setText(choices.get(1));
        t3.setText(choices.get(2));

    }

    public void selectOptionOne(View view) {
        selected(0);
    }

    public void selectOptionTwo(View view) {
        selected(1);
    }

    public void selectOptionThree(View view) {
        selected(2);
    }

    private void updateScore() {
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText("Score: " + score);
        HomeActivity.highScore = score;
    }

    private void selected(int selection) {
        roundDone = true;
        if(selection == correctIndex) {
            score += current + 1;
            updateScore();
            //TODO you win!
        }
        TextView correct;
        if(correctIndex == 0) correct = (TextView) findViewById(R.id.option1);
        else if(correctIndex == 1) correct = (TextView) findViewById(R.id.option2);
        else correct = (TextView) findViewById(R.id.option3);

        correct.setBackgroundColor(getResources().getColor(R.color.win_color));

        current = 1;

    }

    public void loadNextRound(View view) {
        if(!roundDone) return;
        roundDone = false;
        TextView correct;
        if(correctIndex == 0) correct = (TextView) findViewById(R.id.option1);
        else if(correctIndex == 1) correct = (TextView) findViewById(R.id.option2);
        else correct = (TextView) findViewById(R.id.option3);
        correct.setBackgroundColor(getResources().getColor(correctIndex == 1 ? R.color.menu_play : R.color.menu_scores));

        roundNumber++;
        if(roundNumber == 6) {
            HomeActivity.highScore = score;
            this.finish();
            return;
        }
        i++;
        resID = getResources().getIdentifier(randRefList.get(i) + "10", "drawable", getPackageName());

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(resID);
        updateOptions();

        Runnable timedTask = new Runnable() {
            public void run() {
                current = 10;
                cycleImage();
            }
        };
        new Thread(timedTask).start();
    }

    private void cycleImage() {
        final ImageView image = (ImageView) findViewById(R.id.image);
        while(current >= 0) {
            if (current == 10) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 9) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 8) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 7) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 6) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 5) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 4) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 3) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 2) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            if (current == 1) resID = getResources().getIdentifier(randRefList.get(i) + (current - 1), "drawable", getPackageName());
            current--;

            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    image.setImageResource(resID);
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    Cursor getRandom(){
        /* query(FROM TABLE, STRING[] SELECT COLUMNS, )*/
        String[] columns = {ImageContract.ImageEntry.COLUMN_IMAGE_REFERENCE,
                ImageContract.ImageEntry.COLUMN_IMAGE_CATEGORY,
                ImageContract.ImageEntry.COLUMN_IMAGE_DISCRIPTION,
                ImageContract.ImageEntry.COLUMN_ALT_IMAGE_DISCRIPTION};
        String orderBy = "RANDOM()";

        Cursor cursor = getContentResolver().query(ImageContract.ImageEntry.CONTENT_URI, columns, null, null, orderBy);

        return cursor;
    }

    ArrayList<String> getRefArray(Cursor cursor){
        final ArrayList<String> randRefList=new ArrayList<String>();

        cursor.moveToFirst();

        for(int i=0; i < (cursor.getCount());i++) {
            randRefList.add(cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.COLUMN_IMAGE_REFERENCE)));
            cursor.moveToNext();
        }

        return randRefList;
    }

    ArrayList<String> getDiscArray(Cursor cursor){
        final ArrayList<String> randDiscList=new ArrayList<String>();

        cursor.moveToFirst();

        for(int i=0; i < (cursor.getCount());i++) {
            randDiscList.add(cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.COLUMN_IMAGE_DISCRIPTION)));
            cursor.moveToNext();
        }
        return randDiscList;
    }
}
