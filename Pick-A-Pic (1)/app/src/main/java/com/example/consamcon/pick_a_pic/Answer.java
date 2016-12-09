package com.example.consamcon.pick_a_pic;

/**
 * Created by Samir on 11/22/16.
 */
public class Answer {

    private String songName, songTitle;

    public Answer(String songName, String songTitle) {

        this.songName = songName;
        this.songTitle = songTitle;
    }

    public String getSongName () {return songName;}

    public String getSongTitle() {return songTitle;}
}
