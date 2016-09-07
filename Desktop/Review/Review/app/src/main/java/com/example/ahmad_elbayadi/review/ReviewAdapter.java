package com.example.ahmad_elbayadi.review;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
//we first extending array adapter of review object
public class ReviewAdapter extends ArrayAdapter<Review> {
   //constructor that have context and list of reviews and an arguments
    public ReviewAdapter(Activity context, List<Review> Review)
    {
        super(context,0,Review);
    }
    public View getView(int position , View convertView , ViewGroup parent)
    {

        //to get the position
        final Review review= getItem(position);
        //Log for testing
        Log.i("inrooooroooroooroo",position+"");

        //when convert view equal null inflate  R.layout.reviews
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reviews, parent, false);
        }

        //casting an textview to show the author name
        TextView Author=(TextView)convertView.findViewById(R.id.Review_Author_Name);
        //set the author name to the textview
        Author.setText("\""+review.getReview_Autor()+"\"");

        //casting an textview to show the content
        TextView Content=(TextView)convertView.findViewById(R.id.txtContent);
        //set the content to the textview
        Content.setText("     "+review.getReview_Content());

        return convertView;
    }
}

