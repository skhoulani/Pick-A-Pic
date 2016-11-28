package com.mci.db.data;

/**
 * Created by ConnorMcLaughlin on 11/21/2016.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.mci.db.data.ImageContract.ImageEntry;

public class ImageProvider extends ContentProvider{
    public static final String LOG_TAG = ImageProvider.class.getSimpleName();

    private static final int IMAGES = 100;
    private static final int IMAGE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ImageContract.CONTENT_AUTHORITY, ImageContract.PATH_IMAGES, IMAGES);

        sUriMatcher.addURI(ImageContract.CONTENT_AUTHORITY, ImageContract.PATH_IMAGES + "/#", IMAGE_ID);
    }
    private ImageDbHelper mDbHelper;

    @Override
    public boolean onCreate(){
        mDbHelper = new ImageDbHelper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){
            case IMAGES:
                cursor = database.query(ImageEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case IMAGE_ID:
                selection = ImageEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ImageEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case IMAGES:
                return insertImage(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertImage(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(ImageEntry.COLUMN_IMAGE_REFERENCE);
        if (name == null) {
            throw new IllegalArgumentException("Image requires name reference");
        }

        // Check that the gender is valid
        String disc = values.getAsString(ImageEntry.COLUMN_IMAGE_DISCRIPTION);
        if (disc == null) {
            throw new IllegalArgumentException("Pet requires valid gender");
        }




        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(ImageEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case IMAGES:
                return updateImage(uri, contentValues, selection, selectionArgs);
            case IMAGE_ID:
                // For the IMAGE_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ImageEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateImage(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateImage(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(ImageEntry.COLUMN_IMAGE_REFERENCE)) {
            String name = values.getAsString(ImageEntry.COLUMN_IMAGE_REFERENCE);
            if (name == null) {
                throw new IllegalArgumentException("Image requires name reference");
            }
        }


        if (values.containsKey(ImageEntry.COLUMN_IMAGE_DISCRIPTION)) {
            Integer disc = values.getAsInteger(ImageEntry.COLUMN_IMAGE_DISCRIPTION);
            if (disc == null ) {
                throw new IllegalArgumentException("Image requires default discription");
            }
        }




        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(ImageEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case IMAGES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ImageEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case IMAGE_ID:
                // Delete a single row given by the ID in the URI
                selection = ImageEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ImageEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case IMAGES:
                return ImageEntry.CONTENT_LIST_TYPE;
            case IMAGE_ID:
                return ImageEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
