package com.example.ahmad_elbayadi.review;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
//we first extending array adapter of Trailer object
public class TrailersAdapter extends ArrayAdapter<Trailer> {

    //constructor that have context and list of Trailers and an arguments
    public TrailersAdapter(Activity context, ArrayList<Trailer> Trailer) {super(context, 0, Trailer);}
    public View getView(int position , View convertView , ViewGroup parent)
    {
        //to get the position
        Trailer trailer= getItem(position);

        //when convert view equal null inflate  R.layout.trailer
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer, parent, false);
        }

        //casting an textview to show the trailer name
        TextView Trailer_Name=(TextView)convertView.findViewById(R.id.txtTrailer);
        //set the trailer name to the textview
        Trailer_Name.setText(trailer.getTrailer_Name());

        //casting the image view of intro image of trailer
        ImageView Trailer_Image=(ImageView)convertView.findViewById(R.id.list_trailer_icon);
        String YouTube_Image = "http://img.youtube.com/vi/" + trailer.getTrailer_key() + "/0.jpg";
        //loading trailer image
        Picasso.with(getContext()).load(YouTube_Image).placeholder(R.drawable.loading).into(Trailer_Image);

        return convertView;
    }

}
