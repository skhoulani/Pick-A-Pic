package com.example.consamcon.pick_a_pic.data;

/**
 * Created by ConnorMcLaughlin on 11/21/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.consamcon.pick_a_pic.data.ImageContract.ImageEntry;

public class ImageDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = ImageDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "images.db";

    private static final int DATABASE_VERSION = 1;

    public ImageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the IMAGE table
        String SQL_CREATE_IMAGE_TABLE =  "CREATE TABLE " + ImageEntry.TABLE_NAME + " ("
                + ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ImageEntry.COLUMN_IMAGE_REFERENCE + " TEXT NOT NULL, "
                + ImageEntry.COLUMN_IMAGE_CATEGORY + " TEXT NOT NULL, "
                + ImageEntry.COLUMN_IMAGE_DISCRIPTION + " TEXT NOT NULL, "
                + ImageEntry.COLUMN_ALT_IMAGE_DISCRIPTION + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_IMAGE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
