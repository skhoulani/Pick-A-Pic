package com.example.consamcon.pick_a_pic;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.consamcon.pick_a_pic.data.ImageContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbCleanup();
        insertAll();

        setContentView(R.layout.activity_main);


    }

    public void loadGame(View view) {
        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);

        // Start the new activity
        startActivity(gameIntent);
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
    void dbCleanup(){
        getContentResolver().delete(ImageContract.ImageEntry.CONTENT_URI, ImageContract.ImageEntry._ID + "> 0",
                null);
    }
    void insertAll(){
        insertValues("africa", "maps", "Africa", "Continent");
        insertValues("asia", "maps", "Asia", "Continent");
        insertValues("bear", "animals", "Bear", "Grizily");
        insertValues("biden", "People", "Biden", "VP");
        insertValues("brady", "People", "Brady", "GOAT");
        insertValues("canada", "Map", "Canada", "NA Country");
//        insertValues("cereal", "Food", "Cereal", "Breakfast Food");
        insertValues("dog", "Animals", "Dog", "Golden Retreiver");
        insertValues("fish", "Animals", "Fish", "Coral Reef");
        insertValues("gorilla", "Animals", "Gorilla", "Harambe");
        insertValues("kitten", "Animals", "Kitten", "Cat");
        insertValues("lemur", "Animals", "Lemur", "Ring Tailed Lemur");
        insertValues("lion", "Animals", "Lion", "King of Jungle");
        insertValues("obama", "People", "Obama", "President");
        insertValues("panda", "Animals", "Panda", "Bear");
        insertValues("phone", "Things", "Phone", "Smartphone");
        insertValues("pizza", "Food", "Pizza", "Pepperoni");
        insertValues("usa", "Maps", "USA", "America");
    }
    void insertValues(String image_ref, String category, String discription, String alt_discription){

        ContentValues values = new ContentValues();
        values.put(ImageContract.ImageEntry.COLUMN_IMAGE_REFERENCE, image_ref);
        values.put(ImageContract.ImageEntry.COLUMN_IMAGE_CATEGORY, category);
        values.put(ImageContract.ImageEntry.COLUMN_IMAGE_DISCRIPTION, discription);

        if(alt_discription != null){
            values.put(ImageContract.ImageEntry.COLUMN_ALT_IMAGE_DISCRIPTION, alt_discription);
        }
        Uri newUri = getContentResolver().insert(ImageContract.ImageEntry.CONTENT_URI, values);

    }
}

