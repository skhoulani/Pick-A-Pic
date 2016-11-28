package com.mci.db;

/**
 * Created by ConnorMcLaughlin on 11/27/2016.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.net.Uri;

import com.mci.db.data.ImageContract;
import com.mci.db.data.ImageProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import android.content.Context;


public class FileRead {
    private String path;

    public FileRead(String path){this.path = path;}

    private void readFile(File fin, Uri uri) throws IOException {

        String[] valueList;

        FileInputStream fis = new FileInputStream(fin);

        BufferedReader br = new BufferedReader(new FileReader(fin));

        String line = null;
        while((line = br.readLine()) != null) {
            ContentValues values = new ContentValues();
            valueList= line.split(" ");

            for(int i=0; i < valueList.length; i++){
                if(i == 0){
                    values.put(ImageContract.ImageEntry.COLUMN_IMAGE_REFERENCE, valueList[0]);
                }
                else if(i == 1){
                    values.put(ImageContract.ImageEntry.COLUMN_IMAGE_CATEGORY, valueList[1]);
                }
                else if(i == 2){
                    values.put(ImageContract.ImageEntry.COLUMN_IMAGE_DISCRIPTION, valueList[2]);
                }
                else if(i == 3) {
                    values.put(ImageContract.ImageEntry.COLUMN_IMAGE_DISCRIPTION, valueList[3]);
                }
            }
            Uri newUri = ImageProvider.insertImage(ImageContract.ImageEntry.CONTENT_URI, values);
        }
    }


}
