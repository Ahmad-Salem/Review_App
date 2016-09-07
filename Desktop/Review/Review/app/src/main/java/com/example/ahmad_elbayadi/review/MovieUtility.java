package com.example.ahmad_elbayadi.review;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
public class  MovieUtility {

    public static int isChecked(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                MovieAppContract.MovieEntry.CONTENT_URI,
                null,   // projection
                MovieAppContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", // selection
                new String[]{Integer.toString(id)},   // selectionArgs
                null    // sort order
        );

       //to check if there an row or not
        int rows_num = cursor.getCount();
        //Log for testing
        Log.i("rotowqo",rows_num+"");
        //ew close the curser here
        cursor.close();
        return rows_num;
    }

}

