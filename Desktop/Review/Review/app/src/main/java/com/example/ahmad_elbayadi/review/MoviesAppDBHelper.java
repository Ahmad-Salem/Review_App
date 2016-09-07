package com.example.ahmad_elbayadi.review;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */

// we first  extending the SQLiteOpenHelper
public class MoviesAppDBHelper extends SQLiteOpenHelper {

    //we defingin the database name and version
    static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    //the constructor takes the context as an argument
    public MoviesAppDBHelper(Context context) {
        //then we pass the database name ,database version and context to its super class
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //inside onCreate method we write query to create the table
        final String MOVIE_TABLE_CREATE = "CREATE TABLE " + MovieAppContract.MovieEntry.TABLE_NAME + " (" +
                MovieAppContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieAppContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieAppContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieAppContract.MovieEntry.COLUMN_MAIN_IMAGE + " TEXT, " +
                MovieAppContract.MovieEntry.COLUMN_DESCRIPTION + " TEXT, " +
                MovieAppContract.MovieEntry.COLUMN_RATING + " TEXT, " +
                MovieAppContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                MovieAppContract.MovieEntry.COLUMN_RELEASED_DATE + " TEXT);";

        db.execSQL(MOVIE_TABLE_CREATE);
        //log for testing
        Log.i("qdsdsdsdaaaa","databse was created");

    }

    //when the database updated we use this method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieAppContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }


}

