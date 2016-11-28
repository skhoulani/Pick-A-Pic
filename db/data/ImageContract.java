package com.mci.db.data;

/**
 * Created by ConnorMcLaughlin on 11/21/2016.
 */
import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

public class ImageContract {
    private ImageContract(){}

    public static final String CONTENT_AUTHORITY = "com.mci.db.images";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_IMAGES = "images";

    public static final class ImageEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_IMAGES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMAGES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMAGES;

        public final static String TABLE_NAME = "images";

        /* ID for use in SQL  Type = INT*/
        public final static String _ID = BaseColumns._ID;
        /* image ref for pulling image from file Type = TEXT */
        public final static String COLUMN_IMAGE_REFERENCE = "image_ref";
        /* category to pull similar images Type = Text  */
        public final static String COLUMN_IMAGE_CATEGORY = "category";
        /* discription of image Type = Text */
        public final static String COLUMN_IMAGE_DISCRIPTION = "discription";
        /* alternative discription Type = Text*/
        public final static String COLUMN_ALT_IMAGE_DISCRIPTION ="alt_discription";

    }
}
