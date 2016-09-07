package com.example.ahmad_elbayadi.review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //define new object DetailActivityFragment
        DetailActivityFragment DetailFragment=new DetailActivityFragment();
        //save the intent in the bundle object
        Bundle bundle=getIntent().getExtras();
        if(savedInstanceState==null)
        {
            //set the bundle to the DetailFragment object
            DetailFragment.setArguments(bundle);
            //inflate the Detail fragment
            getFragmentManager().beginTransaction().replace(R.id.frame_detail, DetailFragment).commit();

        }
    }
}
