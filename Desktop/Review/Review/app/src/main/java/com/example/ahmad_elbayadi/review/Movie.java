package com.example.ahmad_elbayadi.review;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */public class Movie implements Parcelable {
    //defining the object that can be implemented parcelable that can be the fastest in all devices
    //defining the movie all member variables as private
    private String Title;
    private String RatingLevel;
    private String Overview;
    private String ImageURL ;
    private String ReleasedDate;
    private String OriginalLang;
    private int Movie_id;

    //constractor that initailizing all of these member variables
    public Movie(String Title, String RatingLevel, String Overview, String ImageURL, String ReleasedDate,String OriginalLang,int Movie_id)
    {
        this.Movie_id=Movie_id;
        this.Title = Title;
        this.RatingLevel = RatingLevel;
        this.Overview = Overview;
        this.ImageURL = ImageURL;
        this.ReleasedDate = ReleasedDate;
        this.OriginalLang=OriginalLang;
    }

    //anoyher constuctor to get data from the cursor object that passed to movie and
    //initailizing all of its member varibles with it .
    public Movie(Cursor cursor) {
        this.Movie_id = cursor.getInt(MainActivityFragment.COL_MOVIE_ID);
        this.Title = cursor.getString(MainActivityFragment.COL_TITLE);
        this.ImageURL = cursor.getString(MainActivityFragment.COL_MAIN_IMAGE);
        this.Overview = cursor.getString(MainActivityFragment.COL_DESCRIPTION);
        this.RatingLevel = cursor.getString(MainActivityFragment.COL_RATING);
        this.OriginalLang = cursor.getString(MainActivityFragment.COL_ORGINAIL_LANGUAGE);
        this.ReleasedDate = cursor.getString(MainActivityFragment.COL_RELEASED_DATE);
    }
    //read data from parcel and put it in movie variables
    private Movie(Parcel in){
        Title = in.readString();
        RatingLevel = in.readString();
        Overview = in.readString();
        ImageURL = in.readString();
        ReleasedDate = in.readString();
        OriginalLang = in.readString();
        Movie_id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    //setter and getter methods
    public void setTitle(String Title)
    {
        this.Title=Title;
    }
    public String getTitle()
    {
        return Title;
    }
    public String getRatingLevel()
    {
        return RatingLevel;
    }
    public void setRatingLevel(String RatingLevel)
    {
        this.RatingLevel=RatingLevel;
    }
    public String getReleasedDate()
    {
        return ReleasedDate;
    }
    public void setReleasedDate(String ReleasedDate)
    {
        this.ReleasedDate=ReleasedDate;
    }
    public String getOverview()
    {
        return Overview;
    }
    public void setOverview(String Overview)
    {
        this.Overview=Overview;
    }
    public String getImageURL()
    {
        return ImageURL;
    }
    public void setImageURL(String ImageURL)
    {
        this.ImageURL=ImageURL;
    }
    public int getMovie_id()
    {
        return Movie_id;
    }
    public void setMovie_id(int Movie_id)
    {
        this.Movie_id=Movie_id;
    }
    public String getOriginalLang()
    {
        return OriginalLang;
    }
    public void setOriginalLang(String OriginalLang)
    {
        this.OriginalLang=OriginalLang;
    }



    //write to parcel process
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(RatingLevel);
        parcel.writeString(Overview);
        parcel.writeString(ImageURL);
        parcel.writeString(ReleasedDate);
        parcel.writeString(OriginalLang);
        parcel.writeInt(Movie_id);
    }

    //for crating the parcel
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

}
