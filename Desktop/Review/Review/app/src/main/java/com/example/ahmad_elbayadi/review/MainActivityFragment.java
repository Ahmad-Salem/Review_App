package com.example.ahmad_elbayadi.review;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
public class MainActivityFragment extends Fragment {

    private MovieInterface mInterface;
    private static final String SORT_KEY = "sort_key";
    private static final String MOVIE_KEY = "movies";
    private GridView gridview;
    private Movie movie;
    private ArrayList<Movie> list;
    private static final String Sort_Setting = "sort_setting";
    private static final String Descending_Popularity = "popular";
    private static final String Descending_rating = "top_rated";
    private String FAVORITE_ITEMS="favorites";
    private ProgressBar progressBar;
    private String sorting_method = Descending_Popularity;
    private MainGridAdapter Adapter;
    private ArrayList<Movie> MovieList;
    private ConDetection Con;
    private boolean isConnected = false;
    private LinearLayout linearLayout;
    private Button Retry;
    private String MovieKey="movies";


    //Movie columns that accessed from MovieAppContract.MovieEntry
    private static final String[] MOVIE_COLUMNS = {
            MovieAppContract.MovieEntry._ID,
            MovieAppContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieAppContract.MovieEntry.COLUMN_TITLE,
            MovieAppContract.MovieEntry.COLUMN_MAIN_IMAGE,
            MovieAppContract.MovieEntry.COLUMN_DESCRIPTION,
            MovieAppContract.MovieEntry.COLUMN_RATING,
            MovieAppContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieAppContract.MovieEntry.COLUMN_RELEASED_DATE
    };


    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_MAIN_IMAGE = 3;
    public static final int COL_DESCRIPTION = 4;
    public static final int COL_RATING = 5;
    public static final int COL_ORGINAIL_LANGUAGE = 6;
    public static final int COL_RELEASED_DATE = 7;




    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

        if(savedInstanceState == null || !savedInstanceState.containsKey(MovieKey)) {
            //initailizing the movie list
            MovieList = new ArrayList<Movie>();
            //Log for testing
            Log.d("intro","inside if statement");
        }
        else {
            //initailizing the movie list
            MovieList = savedInstanceState.getParcelableArrayList(MovieKey);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        //casting popularity option
        MenuItem action_popularity = menu.findItem(R.id.group_item_popularity);
        //casting rated level option
        MenuItem action_rating_level = menu.findItem(R.id.group_item_rating);
        //casting favourite option
        MenuItem action_Favorite = menu.findItem(R.id.group_item_rating);

        //when we need top filter results by popularity
        if (sorting_method.contentEquals(Descending_Popularity)) {
            //if popularity option dosenot checked
            //check it
            if (!action_popularity.isChecked()) {
                action_popularity.setChecked(true);
            }
        } else if (sorting_method.contentEquals(Descending_rating)) {
            //if rating option dosenot checked
            //check it
            if (!action_rating_level.isChecked()) {
                action_rating_level.setChecked(true);
            }
        }else if(sorting_method.contentEquals(FAVORITE_ITEMS))
        {
            //if favourite option dosenot checked
            //check it
            if(!action_Favorite.isChecked())
            {
                action_Favorite.setChecked(true);
            }
        }



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            //sorting by the most popular
            case R.id.group_item_popularity:
                //if populaity checked make it un checked
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                //setting the sorting method
                sorting_method = Descending_Popularity;
                updateMovieData(sorting_method);
                return true;

            //sorting by the most rated
            case R.id.group_item_rating:
                //if rating checked make it un checked
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                //setting the sorting method
                sorting_method = Descending_rating;
                updateMovieData(sorting_method);
                return true;
            // favorites
            case R.id.group_item_favorite:
                //if favorite checked make it un checked
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                //setting the sorting methods
                sorting_method = FAVORITE_ITEMS;
                updateMovieData(sorting_method);
                return true;

            // default option
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //defining an object of ConDetection and give it Context to initailize it constructor
        Con = new ConDetection(getActivity());
        //check if there an internet connection
        isConnected = Con.isConnectingToInternet();


        //infalte the main Fragment
        View rootView= inflater.inflate(R.layout.main_grid_fragment, container, false);

        //casting grid view
        gridview=(GridView)rootView.findViewById(R.id.main_grid_view);
        //casting the linear layout that display the messages when there is not internet connection .
        linearLayout=(LinearLayout)rootView.findViewById(R.id.LinearLayout);
        //casting the retry button
        Retry=(Button)rootView.findViewById(R.id.retry);


        if (isConnected) {
            //there is an internet connection
            //we set the lineaner layout to invisible and the gridview to visible
            linearLayout.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            updateMovieData(sorting_method);
            isConnected = false;
        }else
        {
            //there is an internet connection
            //we set the lineaner layout to visible and the gridview to invisible
            linearLayout.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
            //listener when the retry button clicked
            Retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if there an internet connetion
                    if(Con.isConnectingToInternet())
                    {
                        //we set the lineaner layout to invisible and the gridview to visible
                        linearLayout.setVisibility(View.GONE);
                        gridview.setVisibility(View.VISIBLE);
                        updateMovieData(sorting_method);
                        isConnected = false;
                    }else{
                        //toast messge when the wifi doesnot enabled
                        Toast.makeText(getActivity(),"You should Enable Your WIFI Connection ",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        //Listener when grideview clicked
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //get the movie object and set it to the ineterface
                Movie movie =Adapter.getItem(position);
                mInterface.MovieChoosed(movie);

            }
        });


        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(SORT_KEY)) {
                sorting_method = savedInstanceState.getString(SORT_KEY);
            }

            if (savedInstanceState.containsKey(MOVIE_KEY)) {
                MovieList = savedInstanceState.getParcelableArrayList(MOVIE_KEY);
                Adapter=new MainGridAdapter(getActivity(),MovieList);
                gridview.setAdapter(Adapter);
            } else {
                updateMovieData(sorting_method);
            }
        } else {
            updateMovieData(sorting_method);
        }

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!sorting_method.contentEquals(Descending_Popularity)) {
            outState.putString(SORT_KEY, sorting_method);
        }
        if (MovieList != null) {
            outState.putParcelableArrayList(MOVIE_KEY, MovieList);
        }
        super.onSaveInstanceState(outState);
    }

    // update weather function
    private void updateMovieData(String sorting_method)
    {

        if (!sorting_method.contentEquals(FAVORITE_ITEMS)) {
            //to fetch movies sorted by popularity or by rating level
            new FetchMovieTask().execute(sorting_method);
        } else {
            //to fetch favorited movie
            new FetchMoviesFavoratedTask(getActivity()).execute();
        }

    }



    //async task to get the Movie Task
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        //get array list of movies
        public ArrayList<Movie> getMovieDataFromJson(String movieJsonString)
                throws JSONException {
            //Log for testing
            Log.d("inside","hello getWeatherDataFromJson");

            // These are the names of the JSON objects that need to be extracted.
            final String RESULT = "results";

            JSONObject movieObJson = new JSONObject(movieJsonString);
            JSONArray movieArray = movieObJson.getJSONArray(RESULT);

            //initailizing the list
            list = new ArrayList<Movie>();

            for(int i = 0; i < movieArray.length(); i++) {
                //initailizing the movie object
                movie= new Movie("","","","","","",0);
                // Get the JSON object representing the movie
                JSONObject movieObj = movieArray.getJSONObject(i);

                final String POSTER_PATH="poster_path";
                final String RELEASE_DATE="release_date";
                final String OVERVIEW="overview";
                final String VOTE_AVERAGE="vote_average";
                final String ORIGINAL_TITLE="original_title";
                final String ORIGINAL_LANGUAGE="original_language";
                final String ID="id";
                //setting the movie object
                movie.setImageURL(movieObj.getString(POSTER_PATH));
                movie.setReleasedDate(movieObj.getString(RELEASE_DATE));
                movie.setOverview(movieObj.getString(OVERVIEW));
                movie.setRatingLevel(movieObj.getString(VOTE_AVERAGE));
                movie.setTitle(movieObj.getString(ORIGINAL_TITLE));
                movie.setOriginalLang(movieObj.getString(ORIGINAL_LANGUAGE));
                movie.setMovie_id(movieObj.getInt(ID));
                list.add(movie);
            }

            return list;
        }
        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            Log.d("inside","hello in don in background");

            //   Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String format = "json";

            try {
                // Construct the URL
                final String FORECAST_BASE_URL =
                        "https://api.themoviedb.org/3/movie/"+params[0]+"?";
                //key for the api
                final String APPID_PARAM = "api_key";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_APP_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to MovieAPI, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie string: " + movieJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);

                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the data.
            return null;
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            if (result != null) {

                Log.d("intro","inside onpostexecute");
                MovieList=result;
                //setting the adapter and the gridview
                Adapter= new MainGridAdapter(getActivity(),MovieList);
                gridview.setAdapter(Adapter);
                // New data is back from the server.  Hooray!

            }

        }
    }


    //async task to get favourited movie
    public class FetchMoviesFavoratedTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        //defining the context
        private Context mContext;

        //the constructor initailizing the context
        public FetchMoviesFavoratedTask(Context context) {
            mContext = context;
        }

        //get movie data from the cursor
        private ArrayList<Movie> getMoviesFavoritedDataFromCursor(Cursor cursor) {
            //defining and initializing the arralist of results
            ArrayList<Movie> results = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    //take every movie object an put it in results arraylist
                    Movie movie = new Movie(cursor);
                    results.add(movie);
                } while (cursor.moveToNext());
                //we close the cursor here
                cursor.close();
            }
            //then we return the results.....
            return results;
        }

        //method running in the background .....
        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            //defining the cursor objest then we use the content resolver to deal with content provider
            Cursor cursor = mContext.getContentResolver().query(
                    MovieAppContract.MovieEntry.CONTENT_URI,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
            //then we return the data from the cursor
            return getMoviesFavoritedDataFromCursor(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies != null) {
                if (Adapter != null) {
                    //setting the movies into MovieList
                    MovieList=movies;
                    //initailizing the Adapter and the grid view ...
                    Adapter= new MainGridAdapter(getActivity(),MovieList);
                    gridview.setAdapter(Adapter);
                }

            }
        }
    }


    // set the value for the MovieInterface object
    public void setMovieInterface(MovieInterface Interface)
    {
        mInterface=Interface;
    }

}
