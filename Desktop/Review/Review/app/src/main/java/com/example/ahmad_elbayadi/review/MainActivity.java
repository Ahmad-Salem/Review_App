package com.example.ahmad_elbayadi.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements MovieInterface{

    /**the flag that determine
     if the device in phone mode or in tablet mode
    */
    private Boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // casting the detail frame layout
        FrameLayout frame_detail=(FrameLayout)findViewById(R.id.frame_detail);
        if(frame_detail == null)
        {
            //single pane (phone mode)
            mTwoPane=false;
        }
        else
        {
            //two pane (tablet mode)
            mTwoPane=true;
        }

        if(savedInstanceState==null)
        {

            //defining an object of MainActivityFragment
            MainActivityFragment MainFragment=new MainActivityFragment();
            //setting the interface
            MainFragment.setMovieInterface(this);
           //setting the main fragment into frame layout .
            getFragmentManager().beginTransaction().replace(R.id.frame_main, MainFragment).commit();

        }
    }

    //the method that overrided because we implement the interface (Moview interface)
    //this method accept a movie object
    @Override
    public void MovieChoosed(Movie movie) {

        if(mTwoPane)
        {
            //case two pane ui
            DetailActivityFragment DetailFragment= new DetailActivityFragment();
            Bundle bundle=new Bundle();
            bundle.putParcelable("PARCELABLE_KEY",movie);
            DetailFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.frame_detail, DetailFragment).commit();

        }
        else{
            //case single pane ui
            Intent i = new Intent(this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("PARCELABLE_KEY",movie);
            i.putExtras(bundle);
            this.startActivity(i);

        }

    }
}
