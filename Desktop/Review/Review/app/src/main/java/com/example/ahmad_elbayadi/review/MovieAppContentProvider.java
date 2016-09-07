package com.example.ahmad_elbayadi.review;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
public class MovieAppContentProvider extends ContentProvider {

    private static final UriMatcher Matcher = buildUriMatcher();
    private MoviesAppDBHelper MovieDBHelper;


    static final int MOVIES = 200;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieAppContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieAppContract.PATH_MOVIE, MOVIES);

        return matcher;
    }



    @Override
    public boolean onCreate() {
        //here we create the MovieDBHelper and pass to it the context
        MovieDBHelper = new MoviesAppDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (Matcher.match(uri)) {
            case MOVIES: {
                //get the data from database and put it into retcursor
                retCursor = MovieDBHelper.getReadableDatabase().query(
                        MovieAppContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = Matcher.match(uri);

        switch (match) {
            case MOVIES:
                return MovieAppContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    //for inserting row in the table
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = MovieDBHelper.getWritableDatabase();
        Uri returnUri;

        switch (Matcher.match(uri)) {
            case MOVIES: {
                long _id = db.insert(MovieAppContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = MovieAppContract.MovieEntry.buildMovieUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    //for deleting row from the table
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = MovieDBHelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (Matcher.match(uri)) {
            case MOVIES:
                rowsDeleted = db.delete(
                        MovieAppContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    //for update an row in the table
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = MovieDBHelper.getWritableDatabase();
        int rowsUpdated;

        switch (Matcher.match(uri)) {
            case MOVIES:
                rowsUpdated = db.update(MovieAppContract.MovieEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}

