package com.example.consamcon.pick_a_pic;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import com.example.consamcon.pick_a_pic.data.ImageContract;
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Cursor cursor = getRandom();
        Log.d("cursor", "NUMBER=" + cursor.getCount());
        cursor.moveToFirst();
        String ref = cursor.getString(cursor.getColumnIndex(ImageContract.ImageEntry.COLUMN_IMAGE_REFERENCE));
        Log.d("cursor", "NUMBER=" + ref);



        //final ArrayList<String> randRefList=getRefArray(cursor);
        //final ArrayList<String> randDiscList= getDiscArray(cursor);

/*
        for(int i=0; i < randRefList.size();i++){
            Log.d("cursor", "NUMBER:    " + randRefList.get(i) + "   DISC:  " + randDiscList.get(i));
        }
*/
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
