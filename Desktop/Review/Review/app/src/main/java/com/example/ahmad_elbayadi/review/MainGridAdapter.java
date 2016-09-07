package com.example.ahmad_elbayadi.review;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */public class MainGridAdapter extends ArrayAdapter<Movie> {

    // constructor with argument for context
    private Context context;
    //defining array list of movies
    private ArrayList<Movie> Movie1;
    public MainGridAdapter (Activity context, ArrayList<Movie> Movie)
    {
        //setting the context and movie array list to its super class
        super(context, 0, Movie);
        //initializing the context and the movie arraylist
        this.context=context;
        this.Movie1=Movie;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie Movie = getItem(position);
        //image view that displayed in the grid view
        ImageView imageView = new ImageView(getContext());
        //when the convert view dosent equal null then the adapter create new view for it.
        if(convertView!=null) {
            //set image view properities
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
            //setting the iamge url
            String imageUrl = "http://image.tmdb.org/t/p/w185/" + Movie.getImageURL();
            //Log for testing
            Log.d("intro", imageUrl);
            //picasso library loading the loading image first until it can load the movie image
            Picasso.with(getContext()).load(imageUrl).placeholder(R.drawable.loading).into(imageView);

            return imageView;

        }else{
            return imageView;
        }



    }


}

