package com.example.consamcon.pick_a_pic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.view.Window;

import java.util.ArrayList;

/**
 * Created by Samir on 11/22/16.
 */
public class Menu extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //create song list
       // ArrayList<Song> songlist = new ArrayList<Song>();
       // songlist.add(new Song("Claire de Lune", "by Debussy"));
        //songlist.add(new Song("Moonlight Sonata", "by Beethoven"));

//        SongAdapter adapter = new SongAdapter((this), songlist);
//
//        ListView listView = (ListView) findViewById(R.id.listview_study);
//        listView.setAdapter(adapter);
    }
}

